package com.Edupulse.QuizService.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizResponseRequest {
    @NotNull
    private Long quizId;
    private String responses; // JSON of answers
    private Integer timeTaken;
}