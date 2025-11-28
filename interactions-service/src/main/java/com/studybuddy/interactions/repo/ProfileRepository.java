package com.studybuddy.interactions.repo;

import com.studybuddy.interactions.model.Profile;
import com.studybuddy.interactions.model.Seen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // ← Optionnel mais recommandé
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Long ou le type de ton ID
	 Profile findByStudentId(String studentId);
}