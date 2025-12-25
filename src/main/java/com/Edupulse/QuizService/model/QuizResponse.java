package com.Edupulse.QuizService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// QuizResponse.java (student submission)
@Entity
@Table(name = "quiz_responses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private Long quizId;

    @Column(columnDefinition = "JSON")
    private String responses; // student's answers as JSON

    private Double score; // calculated %

    private Integer timeTaken; // seconds

    private LocalDateTime submittedAt = LocalDateTime.now();
}