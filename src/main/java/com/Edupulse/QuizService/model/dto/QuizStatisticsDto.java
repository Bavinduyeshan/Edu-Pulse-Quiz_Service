package com.Edupulse.QuizService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizStatisticsDto {
    private int completedQuizzes;
    private double averageScore;
    private int totalQuizzes; // Optional: total available quizzes
    private int passedQuizzes; // Optional: number of quizzes passed
}