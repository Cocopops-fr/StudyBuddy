package com.studybuddy.users.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EditProfileForm(

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
        @Pattern(regexp = "MALE|FEMALE|AUTRE", message = "Genre invalide")
        String gender
) {}
