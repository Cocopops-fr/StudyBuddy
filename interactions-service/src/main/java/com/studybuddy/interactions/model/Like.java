	package com.studybuddy.interactions.model;

	import jakarta.persistence.*;
	import java.time.LocalDateTime;

	@Entity
	@Table(name = "likes")
	public class Like {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String likerId;
	    private String likedId;
	    private LocalDateTime createdAt = LocalDateTime.now();

	    // Getters et setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public String getLikerId() { return likerId; }
	    public void setLikerId(String likerId) { this.likerId = likerId; }

	    public String getLikedId() { return likedId; }
	    public void setLikedId(String likedId) { this.likedId = likedId; }

	    public LocalDateTime getCreatedAt() { return createdAt; }
	    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	}


