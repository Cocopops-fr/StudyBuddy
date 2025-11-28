package com.studybuddy.users.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record RegisterForm(

        @NotBlank
        @Size(min = 6, max = 128)
        String password,

        @NotBlank
        @Size(min = 1, max = 64)
        String firstName,

        @NotBlank
        @Size(min = 1, max = 64)
        String lastName,

        @NotNull
        @Past
        LocalDate birthDate,

        @Size(max = 128)
        String university,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "MALE|FEMALE", message = "Genre invalide")
        String gender
) {}
