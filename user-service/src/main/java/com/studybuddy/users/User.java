package com.studybuddy.users;

import java.time.Instant;
import java.time.LocalDate;

public record User(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String university,
        String email,
        String gender,
        Instant createdAt
) {}
