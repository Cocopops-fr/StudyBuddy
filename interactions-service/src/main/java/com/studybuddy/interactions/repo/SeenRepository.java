package com.studybuddy.interactions.repo;

import com.studybuddy.interactions.model.Seen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeenRepository extends JpaRepository<Seen, Long> {
	List<Seen> findByViewerId(String viewerId);
}
