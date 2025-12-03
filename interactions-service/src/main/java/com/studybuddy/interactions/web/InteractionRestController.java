package com.studybuddy.interactions.web;

import com.studybuddy.interactions.service.InteractionService;
import com.studybuddy.interactions.service.RandomProfileService;

import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.studybuddy.interactions.actors.ActorMessage;
import com.studybuddy.interactions.actors.ActorSystem;
import com.studybuddy.interactions.model.Profile;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/users")
public class InteractionRestController {

    private final InteractionService interactionService;
	private final RandomProfileService randomProfileService;

  /*  public InteractionRestController(InteractionService interactionService, RandomProfileService randomProfileService) {
        this.interactionService = interactionService;
        this.randomProfileService = randomProfileService;
    }

    @PostMapping("/{sourceUserId}/like/{targetUserId}")
    public ResponseEntity<Profile> like(
            @PathVariable String sourceUserId,
            @PathVariable String targetUserId) {
        
    	
    	return interactionService.like(sourceUserId, targetUserId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.accepted().build());
      //  interactionService.like(sourceUserId, targetUserId);
    }
*/
    @PostMapping("/{sourceUserId}/dislike/{targetUserId}")
    public void dislike(
            @PathVariable String sourceUserId,
            @PathVariable String targetUserId) {

        interactionService.dislike(sourceUserId, targetUserId);
    }

    @GetMapping("/{userId}/likes")
    public Object getLikes(@PathVariable String userId) {
        return interactionService.getLikes(userId);
    }

    @GetMapping("/{userId}/matches")
    public Object getMatches(@PathVariable String userId) {
        return interactionService.getMatches(userId);
    }
    
    
    // ajout 02/12/2025
    
    @GetMapping("/profile/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable String id) {
        return randomProfileService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/next")
    public ResponseEntity<Profile> getNextProfile(@PathVariable String userId) {
        return randomProfileService.getRandomProfileExceptSeen(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Controller OK !");
    }
    
    // actor
    
    
 // injecter ActorSystem dans InteractionRestController
    private final ActorSystem actorSystem;

    public InteractionRestController(InteractionService interactionService, ActorSystem actorSystem, RandomProfileService randomProfileService) {
        this.interactionService = interactionService;
		this.randomProfileService = randomProfileService;
        this.actorSystem = actorSystem;
    }
/*
    @PostMapping("/{sourceUserId}/like/{targetUserId}")
    public ResponseEntity<?> like(@PathVariable String sourceUserId, @PathVariable String targetUserId) {
        ObjectNode payload = JsonNodeFactory.instance.objectNode();
        payload.put("likerId", sourceUserId);
        payload.put("likedId", targetUserId);
        ActorMessage msg = new ActorMessage("Like", payload, "http-rest");
        actorSystem.actorOf("interaction-actor").tell(msg);
        return ResponseEntity.accepted().build();
    }
 */   
    /*
    @PostMapping("/{sourceUserId}/like/{targetUserId}")
    public ResponseEntity<String> like(@PathVariable String sourceUserId,
                                       @PathVariable String targetUserId) {
        ObjectNode payload = JsonNodeFactory.instance.objectNode();
        payload.put("likerId", sourceUserId);
        payload.put("likedId", targetUserId);

        ActorMessage msg = new ActorMessage("Like", payload, "http-rest");
        actorSystem.actorOf("interaction-actor").tell(msg);

        return ResponseEntity.accepted().body("Message sent to actor");
    }
*/
    
    
    @PostMapping("/{sourceUserId}/like/{targetUserId}/ask")
    public ResponseEntity<?> likeAsk(@PathVariable String sourceUserId,
                                     @PathVariable String targetUserId) {

        ObjectNode payload = JsonNodeFactory.instance.objectNode();
        payload.put("likerId", sourceUserId);
        payload.put("likedId", targetUserId);
        ActorMessage msg = new ActorMessage("Like", payload, "http-rest");

        try {
            CompletableFuture<ActorMessage> future = actorSystem.ask("interaction-actor", msg, Duration.ofSeconds(5));
            ActorMessage response = future.get(); // ou use thenApply / CompletableFuture async handling
            // lire response.payload
            boolean matched = response.payload.get("matched").asBoolean();
            return ResponseEntity.ok(Map.of("matched", matched, "payload", response.payload.toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
