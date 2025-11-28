package com.studybuddy.interactions.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dislikes")
public class Dislike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dislikerId;
    private String dislikedId;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDislikerId() { return dislikerId; }
    public void setDislikerId(String dislikerId) { this.dislikerId = dislikerId; }

    public String getDislikedId() { return dislikedId; }
    public void setDislikedId(String dislikedId) { this.dislikedId = dislikedId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
