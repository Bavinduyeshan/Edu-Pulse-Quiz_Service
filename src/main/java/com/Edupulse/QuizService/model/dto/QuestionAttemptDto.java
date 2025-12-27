package com.Edupulse.QuizService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAttemptDto {
    private QuestionDto question;
    private int userAnswer;
    private boolean isCorrect;
}