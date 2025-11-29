package com.studybuddy.interactions.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InteractionStore {
    private final Map<String, Set<String>> likes = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> dislikes = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> seen = new ConcurrentHashMap<>();
    private final Set<MatchPair> matches = ConcurrentHashMap.newKeySet();

    public void saveLike(String likerId, String likedId) {
        likes.computeIfAbsent(likerId, key -> ConcurrentHashMap.newKeySet()).add(likedId);
    }

    public void saveDislike(String dislikerId, String dislikedId) {
        dislikes.computeIfAbsent(dislikerId, key -> ConcurrentHashMap.newKeySet()).add(dislikedId);
    }

    public void markSeen(String viewerId, String seenId) {
        seen.computeIfAbsent(viewerId, key -> ConcurrentHashMap.newKeySet()).add(seenId);
    }

    public boolean hasReciprocalLike(String likerId, String likedId) {
        return likes.getOrDefault(likedId, Collections.emptySet()).contains(likerId);
    }

    public boolean registerMatch(String student1Id, String student2Id) {
        return matches.add(MatchPair.of(student1Id, student2Id));
    }

    public List<String> getMatches(String studentId) {
        List<String> result = new ArrayList<>();
        for (MatchPair pair : matches) {
            if (pair.contains(studentId)) {
                result.add(pair.other(studentId));
            }
        }
        return result;
    }

    public List<String> getLikes(String studentId) {
        return new ArrayList<>(likes.getOrDefault(studentId, Collections.emptySet()));
    }

    public Set<String> getSeen(String studentId) {
        return seen.getOrDefault(studentId, Collections.emptySet());
    }

    private record MatchPair(String studentA, String studentB) {
        static MatchPair of(String a, String b) {
            if (a.compareTo(b) <= 0) {
                return new MatchPair(a, b);
            }
            return new MatchPair(b, a);
        }

        boolean contains(String studentId) {
            return studentA.equals(studentId) || studentB.equals(studentId);
        }

        String other(String studentId) {
            return studentA.equals(studentId) ? studentB : studentA;
        }
    }
}