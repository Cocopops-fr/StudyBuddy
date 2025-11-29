package com.studybuddy.users.dto;

public record UserProfileDto(
        Long id,
        String studentId,
        String fullname,
        String skills,
        String campus
) {
}