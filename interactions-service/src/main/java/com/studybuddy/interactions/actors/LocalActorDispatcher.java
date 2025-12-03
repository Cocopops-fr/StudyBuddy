package com.studybuddy.interactions.actors;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LocalActorDispatcher {
    private final Map<String, Actor> registry = new ConcurrentHashMap<>();

    public void register(String name, Actor actor) {
        registry.put(name, actor);
        actor.start();
    }

    public boolean unregister(String name) {
        Actor a = registry.remove(name);
        if (a != null) {
            a.stop();
            return true;
        }
        return false;
    }

    public boolean exists(String name) {
        return registry.containsKey(name);
    }

    public void deliverToLocal(String name, ActorMessage msg) {
        Actor actor = registry.get(name);
        if (actor != null) {
            actor.tell(msg);
        } else {
            throw new IllegalArgumentException("Actor not found: " + name);
        }
    }
}
