package com.studybuddy.interactions.service;

import com.studybuddy.interactions.model.Profile;
//import com.studybuddy.interactions.repo.*;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class InteractionService {

//    private final LikeRepository likeRepo;
//    private final DislikeRepository dislikeRepo;
//    private final SeenRepository seenRepo;
//    private final MatchRepository matchRepo;
//    private final ProfileRepository profileRepo;
//
//    public InteractionService(LikeRepository likeRepo,
//                              DislikeRepository dislikeRepo,
//                              SeenRepository seenRepo,
//                              MatchRepository matchRepo,
//                              ProfileRepository profileRepo) {
//    	
//        this.likeRepo = likeRepo;
//        this.dislikeRepo = dislikeRepo;
//        this.seenRepo = seenRepo;
//        this.matchRepo = matchRepo;
//        this.profileRepo = profileRepo;
//    }

    private final InteractionStore store;
    private final RandomProfileService randomProfileService;

    public InteractionService(InteractionStore store, RandomProfileService randomProfileService) {
        this.store = store;
        this.randomProfileService = randomProfileService;
    }
    
    
    //@Transactional
    public Optional<Profile> like(String likerId, String likedId) {
        // enregistrer le like
//
//        Like l = new Like();
//        l.setLikerId(likerId);
//        l.setLikedId(likedId);
//        likeRepo.save(l);
//        
//
//        // check réciproque
//        Optional<Like> reciprocal = likeRepo.findAll().stream()
//                .filter(x -> x.getLikerId().equals(likedId) && x.getLikedId().equals(likerId))
//                .findFirst();
//
//        if (reciprocal.isPresent()) {
//            // éviter doublon
//            boolean exists = matchRepo.findAll().stream()
//                    .anyMatch(m -> (m.getStudent1Id().equals(likerId) && m.getStudent2Id().equals(likedId))
//                            || (m.getStudent1Id().equals(likedId) && m.getStudent2Id().equals(likerId)));
//            if (!exists) {
//                Match match = new Match();
//                match.setStudent1Id(likerId);
//                match.setStudent2Id(likedId);
//                matchRepo.save(match);
//            }
//
//            // 🔥 ici on renvoie le profil de la personne avec qui le match a eu lieu
//            Profile matchedProfile = profileRepo.findByStudentId(likedId);
//            return Optional.ofNullable(matchedProfile);
    	
        store.saveLike(likerId, likedId);
        store.markSeen(likerId, likedId);

        if (store.hasReciprocalLike(likerId, likedId) && store.registerMatch(likerId, likedId)) {
            return randomProfileService.getById(likedId);    	
    	
        }

        // sinon aucun match
        return Optional.empty();
    }

    
    public void dislike(String dislikerId, String dislikedId) {
//        Dislike d = new Dislike();
//        d.setDislikerId(dislikerId);
//        d.setDislikedId(dislikedId);
//        dislikeRepo.save(d);
        store.saveDislike(dislikerId, dislikedId);
        store.markSeen(dislikerId, dislikedId);
    }

    public void markSeen(String viewerId, String seenId) {
//        Seen s = new Seen();
//        s.setViewerId(viewerId);
//        s.setSeenId(seenId);
//        seenRepo.save(s);
    	store.markSeen(viewerId, seenId);
    }
    
    public List<String> getMatches(String studentId) {
//        return matchRepo.findAll().stream()
//                .filter(m ->
//                        m.getStudent1Id().equals(studentId)
//                        || m.getStudent2Id().equals(studentId)
//                )
//                .map(m ->
//                        m.getStudent1Id().equals(studentId)
//                                ? m.getStudent2Id()
//                                : m.getStudent1Id()
//                )
//                .toList();
    	 return store.getMatches(studentId);
    }
    
    public List<String> getLikes(String studentId) {
//        return likeRepo.findAll().stream()
//                .filter(l -> l.getLikerId().equals(studentId))  // on prend les personnes que ce studentId a likées
//                .map(Like::getLikedId)  // on ne renvoie que les IDs likés
//                .toList();
    	return store.getLikes(studentId);
    }

    public List<String> getSeen(String studentId) {
        return store.getSeen(studentId).stream().toList();
    }
    
}


