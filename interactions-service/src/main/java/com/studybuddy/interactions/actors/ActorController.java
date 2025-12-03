package com.studybuddy.interactions.actors;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorSystem actorSystem;

    public ActorController(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    @PostMapping("/{actorName}/messages")
    public ResponseEntity<String> postMessage(@PathVariable String actorName,
                                              @RequestBody ActorMessage message) {
        try {
            ActorRef ref = actorSystem.actorOf(actorName);
            ref.tell(message);
            return ResponseEntity.accepted().body("Message delivered to " + actorName);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /*
    @GetMapping("/{actorName}")
    public ResponseEntity<String> getActorStatus(@PathVariable String actorName) {
        if (!actorSystem.actorOf(actorName).equals(null)) {
            return ResponseEntity.ok("Actor exists: " + actorName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
*/
    @GetMapping("/{actorName}")
    public ResponseEntity<String> getActorStatus(@PathVariable String actorName) {
        if (actorSystem.exists(actorName)) {  // <-- USE dispatcher.exists
            return ResponseEntity.ok("Actor exists: " + actorName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
