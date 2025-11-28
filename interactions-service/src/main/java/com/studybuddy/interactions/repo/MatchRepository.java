package com.studybuddy.interactions.repo;

import com.studybuddy.interactions.model.Match;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
	

}
