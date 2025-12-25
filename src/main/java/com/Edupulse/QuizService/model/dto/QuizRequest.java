package com.Edupulse.QuizService.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// QuizRequest.java (create quiz)
@Data
public class QuizRequest {
    @NotBlank
    private String title;
    private String questions; // JSON string
    private Integer timeLimit;
    private Double passThreshold = 60.00;
    private Long lectureId; // optional
    @NotNull
    private Long classId;
}