package com.studybuddy.interactions.web;

import com.studybuddy.interactions.service.InteractionService;
import com.studybuddy.interactions.service.RandomProfileService;
import com.studybuddy.interactions.service.UserServiceClient;

//import jakarta.servlet.http.HttpSession;

import com.studybuddy.interactions.model.Profile;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ProfileUiController {

    private final RandomProfileService randomProfileService;
    private final InteractionService interactionService;
    public final UserServiceClient userServiceClient;


    public ProfileUiController(RandomProfileService randomProfileService,
                               InteractionService interactionService, UserServiceClient userServiceClient) {
        this.randomProfileService = randomProfileService;
        this.interactionService = interactionService;
		this.userServiceClient = userServiceClient;
    }

    @GetMapping("/")
    public String showRandomRedirect() {
        return "redirect:/profile?userId=1";
    }
    
    
    @GetMapping("/profile")
    public String showProfile(@RequestParam("userId") String userId, Model model) {
        Optional<Profile> next = randomProfileService.getRandomProfileExceptSeen(userId);

        if (next.isEmpty()) {
            model.addAttribute("noProfiles", true);
            model.addAttribute("profile", null);
            model.addAttribute("message", "Plus de profils disponibles");
        } else {
            model.addAttribute("profile", next.get());
        }

        model.addAttribute("currentStudent", userId);
        return "profile";
    }

    
    
    @PostMapping("/dislike")
    public String dislike(@RequestParam("id") String id, @RequestParam("userId") String userId) {

        // enregistrer le dislike
//        Profile p = randomProfileService.getById(id);
//        if (p != null) {
//            interactionService.dislike(userId, p.getStudentId());
//        }

        randomProfileService.getById(id)
        .ifPresent(p -> interactionService.dislike(userId, p.getStudentId()));
        
        // marquer le profil comme vu
        interactionService.markSeen(userId, id);

        return "redirect:/profile?userId=" + userId;
    }
    
    @PostMapping("/like")
    public String likeProfile(@RequestParam("id") String id,
                              @RequestParam("userId") String userId) {

        // 1. Récupérer le profil
        userServiceClient.getProfileById(id).ifPresent(profile -> {
            // 2. Enregistrer le like côté interactions-service
            interactionService.like(userId, profile.getStudentId());
            // 3. Marquer comme vu
            interactionService.markSeen(userId, profile.getStudentId());
        });

        // 4. Rediriger vers le prochain profil
        return "redirect:/profile?userId=" + userId;
    }
/*

    @PostMapping("/like")
    public String likeProfile(@RequestParam("id") String id,
                              @RequestParam("userId") String userId,
                              Model model) {

        userServiceClient.getProfileById(id).ifPresent(profile -> {
            interactionService.like(userId, profile.getStudentId());
            interactionService.markSeen(userId, profile.getStudentId());
        });

        Optional<Profile> next = randomProfileService.getRandomProfileExceptSeen(userId);
        return next.map(p -> "redirect:/profile?userId=" + userId)
                   .orElse("noProfiles");  // page “plus de profils”
    }

*/
  /*  @PostMapping("/like")
    public String likeProfile(@RequestParam("id") String id,
                              @RequestParam("userId") String userId,
                              Model model) {

        // enregistrer le like
        Optional<Profile> matchOpt = interactionService.like(userId, id);

        // marquer le profil comme vu
        interactionService.markSeen(userId, id);

        if (matchOpt.isPresent()) {
            Profile matchedProfile = matchOpt.get();
            model.addAttribute("match", matchedProfile);
            model.addAttribute("userId", userId);
            return "matchNotification";
        }


     // sinon afficher un nouveau profil
        Optional<Profile> next = randomProfileService.getRandomProfileExceptSeen(userId);
        interactionService.markSeen(userId, id); // garder ici

        return "redirect:/profile?userId=" + userId; // ne recharge plus rien d'autre avant

    }
*/

    @GetMapping("/matches")
    public String showMatches(@RequestParam("userId") String userId, Model model) {
        var matchIds = interactionService.getMatches(userId);
        var matches = matchIds.stream()
        		.map(randomProfileService::getById) //.map(id -> randomProfileService.getById(id))
        		.flatMap(Optional::stream) //.filter(p -> p != null)
                .toList();

        model.addAttribute("matches", matches);
        model.addAttribute("currentStudent", userId);
        return "matches";
    }
    }

