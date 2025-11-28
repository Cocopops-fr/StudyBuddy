package com.studybuddy.interactions.service;

import com.studybuddy.interactions.model.Profile;
import com.studybuddy.interactions.model.Seen;
import com.studybuddy.interactions.repo.ProfileRepository;
import com.studybuddy.interactions.repo.SeenRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class RandomProfileService {

    private final ProfileRepository profileRepo;
    private final SeenRepository seenRepo;
    private final Random random = new Random();

    public RandomProfileService(ProfileRepository profileRepo, SeenRepository seenRepo) {
        this.profileRepo = profileRepo;
        this.seenRepo = seenRepo;
    }

    public Optional<Profile> getRandomProfileExceptSeen(String viewerId) {
        List<Profile> allProfiles = profileRepo.findAll();
        List<Seen> seenList = seenRepo.findByViewerId(viewerId);

        List<String> seenIds = seenList.stream()
                .map(Seen::getSeenId)
                .collect(Collectors.toList());

        List<Profile> remaining = allProfiles.stream()
                .filter(p -> !seenIds.contains(p.getStudentId()))
                .filter(p -> !p.getStudentId().equals(viewerId))
                .collect(Collectors.toList());

        if (remaining.isEmpty()) return Optional.empty();

        int idx = random.nextInt(remaining.size());
        Profile chosen = remaining.get(idx);

        return Optional.of(chosen);
    }
    
    public Profile getById(String studentId) {
    	return profileRepo.findByStudentId(studentId);
    }
}
