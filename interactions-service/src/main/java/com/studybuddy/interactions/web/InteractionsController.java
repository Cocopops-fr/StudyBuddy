package com.studybuddy.interactions.web;

import com.studybuddy.interactions.model.Profile;
import com.studybuddy.interactions.service.InteractionService;
import com.studybuddy.interactions.service.RandomProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
public class InteractionsController { // tu pourras le renommer InteractionController

    private final InteractionService interactionService;
    private final RandomProfileService randomProfileService;

    public InteractionsController(InteractionService interactionService,
                                     RandomProfileService randomProfileService) {
        this.interactionService = interactionService;
        this.randomProfileService = randomProfileService;
    }

    // POST /api/interactions/like
    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody LikeRequest req) {
        // on se fiche du retour éventuel du service, on applique juste l'effet
        interactionService.like(req.sourceUserId(), req.targetUserId());
        return ResponseEntity.ok().build(); // 200 + body vide
    }

    // POST /api/interactions/dislike
    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody LikeRequest req) {
        interactionService.dislike(req.sourceUserId(), req.targetUserId());
        return ResponseEntity.ok().build(); // 200 + body vide
    }

    // GET /api/interactions/{userId}/matches
    @GetMapping("/{userId}/matches")
    public List<String> getMatches(@PathVariable String userId) {
        return interactionService.getMatches(userId);
    }
/*
    // GET /api/interactions/{userId}/likes  (optionnel)
    @GetMapping("/{userId}/likes")
    public List<String> getLikes(@PathVariable String userId) {
        return interactionService.getLikes(userId);
    }
*/
    // GET /api/interactions/next?userId=...
    @GetMapping("/next")
    public ResponseEntity<Profile> getNextProfile(@RequestParam String userId) {
        return randomProfileService.getRandomProfileExceptSeen(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // 🆕 GET /api/interactions/match?sourceUserId=...&targetUserId=...
    @GetMapping("/match")
    public ResponseEntity<MatchResponse> isMatch(@RequestParam String sourceUserId,
                                                 @RequestParam String targetUserId) {

        // on regarde si targetUserId fait partie des matchs de sourceUserId
        boolean isMatch = interactionService
                .getMatches(sourceUserId)
                .contains(targetUserId);

        return ResponseEntity.ok(new MatchResponse(isMatch));
    }
}

// requête pour /like et /dislike
record LikeRequest(String sourceUserId, String targetUserId) {}

// réponse pour /match
record MatchResponse(boolean isMatch) {}

