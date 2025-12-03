
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

