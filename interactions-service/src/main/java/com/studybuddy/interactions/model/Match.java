package com.studybuddy.interactions.model;

// import jakarta.persistence.*;
import java.time.LocalDateTime;

//@Entity
//@Table(name = "matches")
public class Match {
  //  @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String student1Id;
    private String student2Id;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudent1Id() { return student1Id; }
    public void setStudent1Id(String student1Id) { this.student1Id = student1Id; }

    public String getStudent2Id() { return student2Id; }
    public void setStudent2Id(String student2Id) { this.student2Id = student2Id; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}