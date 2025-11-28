package com.studybuddy.interactions.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seen")
public class Seen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String viewerId;
    private String seenId;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getViewerId() { return viewerId; }
    public void setViewerId(String viewerId) { this.viewerId = viewerId; }

    public String getSeenId() { return seenId; }
    public void setSeenId(String seenId) { this.seenId = seenId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
