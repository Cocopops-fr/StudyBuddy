package com.studybuddy.interactions.service;

import com.studybuddy.interactions.model.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    public UserServiceClient(RestTemplate restTemplate,
                             @Value("${users.service.url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    public List<Profile> getAllProfiles() {
        Profile[] profiles = restTemplate.getForObject(userServiceUrl + "/api/users", Profile[].class);
        if (profiles == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(profiles).toList();
    }

    public Optional<Profile> getProfileById(String studentId) {
        return Optional.ofNullable(restTemplate.getForObject(userServiceUrl + "/api/users/" + studentId, Profile.class));
    }
}