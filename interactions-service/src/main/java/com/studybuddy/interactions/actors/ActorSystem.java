package com.studybuddy.interactions.actors;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.*;

@Component
public class ActorSystem {
    private final LocalActorDispatcher dispatcher;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ActorSystem(LocalActorDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void createLocal(String name, java.util.function.Supplier<Actor> actorFactory) {
        if (dispatcher.exists(name)) {
            throw new IllegalStateException("Actor already exists: " + name);
        }
        Actor actor = actorFactory.get();
        dispatcher.register(name, actor);
    }

    public ActorRef actorOf(String name) {
        return new ActorRef(name, dispatcher);
    }
    
    public boolean exists(String name) {
        return dispatcher.exists(name);
    }


    /**
     * Ask pattern : envoie un message et retourne une CompletableFuture qui sera complétée
     * par la réponse attendue. Timeout si pas de réponse.
     */
    public CompletableFuture<ActorMessage> ask(String targetActorName, ActorMessage msg, Duration timeout) {
        String tempActorName = "reply-" + UUID.randomUUID();
        CompletableFuture<ActorMessage> future = new CompletableFuture<>();

        // Temporary actor that will complete the future when it receives a message
        Actor replyActor = new Actor(tempActorName) {
            @Override
            protected void onReceive(ActorMessage message) throws Exception {
                // complete the future if not already completed
                if (!future.isDone()) {
                    future.complete(message);
                }
                // unregister itself after receiving response
                dispatcher.unregister(tempActorName);
            }
        };

        // register temporary actor
        dispatcher.register(tempActorName, replyActor);

        // set replyTo so target actor knows where to send response
        msg.replyTo = tempActorName;
        // set from for traceability
        if (msg.from == null) msg.from = "ask-" + tempActorName;

        // send
        actorOf(targetActorName).tell(msg);

        // schedule timeout
        ScheduledFuture<?> timeoutHandle = scheduler.schedule(() -> {
            if (!future.isDone()) {
                future.completeExceptionally(new TimeoutException("No reply from actor within " + timeout));
                // ensure temp actor removed
                dispatcher.unregister(tempActorName);
            }
        }, timeout.toMillis(), TimeUnit.MILLISECONDS);

        // when future completes normally or exceptionally, cancel timeout if running
        future.whenComplete((r, ex) -> timeoutHandle.cancel(false));

        return future;
    }
}





/*package com.studybuddy.interactions.actors;

import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ActorSystem {
    private final LocalActorDispatcher dispatcher;

    public ActorSystem(LocalActorDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void createLocal(String name, Supplier<Actor> actorFactory) {
        if (dispatcher.exists(name)) {
            throw new IllegalStateException("Actor already exists: " + name);
        }
        Actor actor = actorFactory.get();
        dispatcher.register(name, actor);
    }

    public ActorRef actorOf(String name) {
        return new ActorRef(name, dispatcher);
    }
}
*/