package com.studybuddy.interactions.repo;

import com.studybuddy.interactions.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    // tu pourras ajouter des méthodes spécifiques plus tard si besoin
}


