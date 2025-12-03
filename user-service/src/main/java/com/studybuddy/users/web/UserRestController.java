package com.studybuddy.users.web;

import com.studybuddy.users.UserEntity;
import com.studybuddy.users.UserRepository;
import com.studybuddy.users.dto.RegisterForm;
import com.studybuddy.users.dto.UserProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserRestController {

    private final UserRepository repository;

    public UserRestController(UserRepository repository) {
        this.repository = repository;
    }

    // 1. Récupérer tous les utilisateurs
    @GetMapping
    public List<UserProfileDto> all() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    // 2. Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(user -> ResponseEntity.ok(toDto(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. Créer un nouvel utilisateur
    @PostMapping("/register")
    public ResponseEntity<UserProfileDto> createUser(@RequestBody RegisterForm form) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(form.email());
        newUser.setFirstName(form.firstName());
        newUser.setLastName(form.lastName());
        newUser.setPasswordHash(BCrypt.hashpw(form.password(), BCrypt.gensalt()));

        UserEntity savedUser = repository.save(newUser);
        return ResponseEntity.status(201).body(toDto(savedUser));
    }

    // 4. Mettre à jour un utilisateur existant
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDto> updateUser(@PathVariable Long id, @RequestBody RegisterForm form) {
        return repository.findById(id)
                .map(user -> {
                    user.setFirstName(form.firstName());
                    user.setLastName(form.lastName());
                    user.setEmail(form.email());
                    if (form.password() != null && !form.password().isEmpty()) {
                        user.setPasswordHash(BCrypt.hashpw(form.password(), BCrypt.gensalt()));
                    }
                    UserEntity updatedUser = repository.save(user);
                    return ResponseEntity.ok(toDto(updatedUser));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 5. Supprimer un utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
                    repository.delete(user); // Supprimer l'utilisateur
                    return ResponseEntity.noContent().build(); // Retourner un statut 204 (No Content)
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Retourner un statut 404 si l'utilisateur n'est pas trouvé
    }

    // Méthode de conversion pour transformer une entité UserEntity en DTO
    private UserProfileDto toDto(UserEntity user) {
        return new UserProfileDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPasswordHash());
    }


}
