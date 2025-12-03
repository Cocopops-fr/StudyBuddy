package com.studybuddy.interactions.actors;

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
