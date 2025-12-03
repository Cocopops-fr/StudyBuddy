package com.studybuddy.interactions.actors;

import com.fasterxml.jackson.databind.JsonNode;
import com.studybuddy.interactions.service.InteractionService;
import com.studybuddy.interactions.model.Profile;

import java.util.Optional;
import java.util.logging.Logger;

public class InteractionActor extends Actor {

    private final InteractionService interactionService;
    private final Logger logger = Logger.getLogger(InteractionActor.class.getName());

    public InteractionActor(String name, InteractionService interactionService) {
        super(name);
        this.interactionService = interactionService;
    }

    @Override
    protected void onReceive(ActorMessage msg) throws Exception {
        logger.info(() -> "[" + getName() + "] got message type=" + msg.type + " from=" + msg.from);
        if ("Like".equalsIgnoreCase(msg.type)) {
            JsonNode p = msg.payload;
            String liker = p.get("likerId").asText();
            String liked = p.get("likedId").asText();

            // intègre ton InteractionService : effectue le like
            Optional<Profile> match = interactionService.like(liker, liked);

            if (match.isPresent()) {
                Profile matched = match.get();
                logger.info(() -> "[" + getName() + "] It's a match with " + matched.getFullname());
            } else {
                logger.info(() -> "[" + getName() + "] no match yet for " + liker + " -> " + liked);
            }
        } else {
            logger.info(() -> "[" + getName() + "] unknown message type: " + msg.type);
        }
    }
}
