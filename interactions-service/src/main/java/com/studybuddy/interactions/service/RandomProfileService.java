package com.studybuddy.interactions.service;

import com.studybuddy.interactions.model.Profile;
//import com.studybuddy.interactions.model.Seen;
//import com.studybuddy.interactions.repo.ProfileRepository;
//import com.studybuddy.interactions.repo.SeenRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Component
public class RandomProfileService {

//    private final ProfileRepository profileRepo;
//    private final SeenRepository seenRepo;
   // private final RestTemplate restTemplate;
    
    //nouveau :
    private final UserServiceClient userServiceClient;
    private final InteractionStore store;
    private final Random random = new Random();
    
    
    public RandomProfileService(InteractionStore store, UserServiceClient userServiceClient) {
        this.store = store;
        // OLD : this.userServiceUrl = userServiceUrl;
        this.userServiceClient = userServiceClient;
    }

    public Optional<Profile> getRandomProfileExceptSeen(String viewerId) {
//        List<Profile> allProfiles = profileRepo.findAll();
//        List<Seen> seenList = seenRepo.findByViewerId(viewerId);
//
//        List<String> seenIds = seenList.stream()
//                .map(Seen::getSeenId)
//                .collect(Collectors.toList());
    	
//        List<Profile> allProfiles = fetchProfiles(); - old (url)
//        List<String> seenIds = store.getSeen(viewerId).stream().toList();
    	List<Profile> allProfiles = userServiceClient.getAllProfiles();
        List<String> seenIds = store.getSeen(viewerId).stream().toList(); // new : api

        List<Profile> remaining = allProfiles.stream()
                .filter(p -> !seenIds.contains(p.getStudentId()))
                .filter(p -> !p.getStudentId().equals(viewerId))
                .toList();

        if (remaining.isEmpty()) return Optional.empty();

        int idx = random.nextInt(remaining.size());
        Profile chosen = remaining.get(idx);

        return Optional.of(chosen);
    }
    
//    public Profile getById(String studentId) {
//    	return profileRepo.findByStudentId(studentId);

    public Optional<Profile> getById(String studentId) {
//        return Optional.ofNullable(restTemplate.getForObject(userServiceUrl + "/api/users/" + studentId, Profile.class));
//    }
//
//    private List<Profile> fetchProfiles() {
//        Profile[] profiles = restTemplate.getForObject(userServiceUrl + "/api/users", Profile[].class);
//        if (profiles == null) {
//            return List.of();
//        }
//        return Arrays.stream(profiles).toList();
    	
        return userServiceClient.getProfileById(studentId);

    }
}
