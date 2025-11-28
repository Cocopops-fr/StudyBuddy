package com.studybuddy.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record LoginForm(
        @NotBlank
        @Email
        String email,
        @NotBlank String password
) {}
