package com.Edupulse.QuizService.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

//feign
@Data
public class UserResponse {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String role;
    private Long gradeId;
    private String gradeName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String institution;
    private String department;
    private String bio;
    private boolean isVerified;
    private String profilePictureUrl;
    private LocalDateTime createdAt;
}