package com.studybuddy.interactions.actors;

import com.studybuddy.interactions.service.InteractionService;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ActorBootstrap {

    private final ActorSystem actorSystem;
    private final InteractionService interactionService;

    public ActorBootstrap(ActorSystem actorSystem, InteractionService interactionService) {
        this.actorSystem = actorSystem;
        this.interactionService = interactionService;
    }
    /*
    @PostConstruct
    public void init() {
        // registre un acteur nommé "interaction-actor"
        actorSystem.createLocal("interaction-actor", () -> new InteractionActor("interaction-actor", interactionService));
    }
*/

        @PostConstruct
        public void init() {
            actorSystem.createLocal("interaction-actor", () -> new InteractionActor("interaction-actor", interactionService, actorSystem));
        }
    }


