package com.studybuddy.interactions.model;

//import jakarta.persistence.*;

//@Entity  // ← Ajoute cette ligne !
//@Table(name = "profiles")  // ← Optionnel mais recommandé
public class Profile {
    
 //   @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String studentId;
    private String fullname;
    private String skills;
    private String campus;
    
    // Constructeur vide OBLIGATOIRE pour JPA
    public Profile() {
    }
    
    // Getters et setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) {  // ← Ajoute ce setter
        this.id = id;
    }
    
    public String getStudentId() { 
        return studentId; 
    }
    
    public void setStudentId(String studentId) { 
        this.studentId = studentId; 
    }
    
    public String getFullname() { 
        return fullname; 
    }
    
    public void setFullname(String fullname) { 
        this.fullname = fullname; 
    }
    
    public String getSkills() { 
        return skills; 
    }
    
    public void setSkills(String skills) { 
        this.skills = skills; 
    }
    
    public String getCampus() { 
        return campus; 
    }
    
    public void setCampus(String campus) { 
        this.campus = campus; 
    }
}