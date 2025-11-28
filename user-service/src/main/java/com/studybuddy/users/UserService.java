package com.studybuddy.users;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.studybuddy.users.dto.EditProfileForm;
import com.studybuddy.users.dto.LoginForm;
import com.studybuddy.users.dto.RegisterForm;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public UserEntity register(RegisterForm form) {
        String email = form.email().trim().toLowerCase();

        if (repo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        UserEntity user = new UserEntity();
        user.setPasswordHash(BCrypt.hashpw(form.password(), BCrypt.gensalt()));
        user.setFirstName(form.firstName().trim());
        user.setLastName(form.lastName().trim());
        user.setBirthDate(form.birthDate());
        user.setUniversity(form.university() != null ? form.university().trim() : null);
        user.setEmail(email);
        user.setGender(form.gender());

        return repo.save(user);
    }

    public Optional<UserEntity> authenticate(LoginForm form) {
        String email = form.email().trim().toLowerCase();
        return repo.findByEmail(email)
                .filter(user -> BCrypt.checkpw(form.password(), user.getPasswordHash()));
    }

    public Optional<UserEntity> findByEmail(String email) {
        return repo.findByEmail(email.trim().toLowerCase());
    }

    @Transactional
    public void deleteAccount(String email) {
        String e = email.trim().toLowerCase();
        repo.findByEmail(e).ifPresent(repo::delete);
    }

    @Transactional
    public UserEntity updateProfile(String currentEmail, EditProfileForm form) {
        String current = currentEmail.trim().toLowerCase();

        UserEntity user = repo.findByEmail(current)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        String newEmail = form.email().trim().toLowerCase();

        if (!newEmail.equalsIgnoreCase(user.getEmail()) && repo.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        user.setFirstName(form.firstName().trim());
        user.setLastName(form.lastName().trim());
        user.setBirthDate(form.birthDate());
        user.setUniversity(form.university() != null ? form.university().trim() : null);
        user.setEmail(newEmail);
        user.setGender(form.gender());

        return repo.save(user);
    }
}
