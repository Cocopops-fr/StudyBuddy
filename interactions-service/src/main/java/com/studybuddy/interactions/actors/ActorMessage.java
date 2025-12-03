/*
package com.studybuddy.interactions.actors;

import com.fasterxml.jackson.databind.JsonNode;

public class ActorMessage {
    public String type;
    public JsonNode payload;
    public String from;

    public ActorMessage() {}

    public ActorMessage(String type, JsonNode payload, String from) {
        this.type = type;
        this.payload = payload;
        this.from = from;
    }
}
*/
package com.studybuddy.interactions.actors;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.UUID;

public class ActorMessage {
    public String id;          // uuid
    public String type;
    public JsonNode payload;
    public String from;        // qui envoie (nom de l'acteur ou "http-rest")
    public String replyTo;     // nom de l'acteur destinataire des réponses (optionnel)

    public ActorMessage() {
        this.id = UUID.randomUUID().toString();
    }

    public ActorMessage(String type, JsonNode payload, String from) {
        this();
        this.type = type;
        this.payload = payload;
        this.from = from;
        this.replyTo = null;
    }
}
