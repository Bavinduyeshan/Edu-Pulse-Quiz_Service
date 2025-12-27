package com.Edupulse.QuizService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttemptDetailDto {
    private Long quizId;
    private String quizTitle;
    private List<QuestionAttemptDto> questions;
    private double score;
    private int timeTaken;
    private LocalDateTime submittedAt;
}