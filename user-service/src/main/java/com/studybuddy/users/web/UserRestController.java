package com.studybuddy.users.web;

import com.studybuddy.users.UserEntity;
import com.studybuddy.users.UserRepository;
import com.studybuddy.users.dto.UserProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserRestController {

    private final UserRepository repository;

    public UserRestController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<UserProfileDto> all() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> byId(@PathVariable Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private UserProfileDto toDto(UserEntity entity) {
        return new UserProfileDto(
                entity.getId(),
                String.valueOf(entity.getId()),
                entity.getFirstName() + " " + entity.getLastName(),
                "",
                entity.getUniversity()
        );
    }
}