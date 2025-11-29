package com.studybuddy.interactions.web;

import com.studybuddy.interactions.service.InteractionService;
import org.springframework.web.bind.annotation.*;
import com.studybuddy.interactions.model.Profile;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/users")
public class InteractionRestController {

    private final InteractionService interactionService;

    public InteractionRestController(InteractionService interactionService) {
        this.interactionService = interactionService;
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
}
