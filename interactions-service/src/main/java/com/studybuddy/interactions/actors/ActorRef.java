package com.studybuddy.interactions.actors;

public class ActorRef {
    private final String actorName;
    private final LocalActorDispatcher localDispatcher;

    public ActorRef(String actorName, LocalActorDispatcher localDispatcher) {
        this.actorName = actorName;
        this.localDispatcher = localDispatcher;
    }

    public String getActorName() { return actorName; }

    public void tell(ActorMessage msg) {
        localDispatcher.deliverToLocal(actorName, msg);
    }
}
