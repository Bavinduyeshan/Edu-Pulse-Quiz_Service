package com.Edupulse.QuizService.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuizResultDto {
    private Long quizId;
    private String title;
    private Long studentId;
    private String studentName;
    private Double score;
    private String passed; // "PASS" / "FAIL"
    private Integer timeTaken;
    private LocalDateTime submittedAt;
}