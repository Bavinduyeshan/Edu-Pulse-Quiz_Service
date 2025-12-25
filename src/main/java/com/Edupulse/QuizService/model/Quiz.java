package com.Edupulse.QuizService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Quiz.java
@Entity
@Table(name = "quizzes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(columnDefinition = "JSON") // Store questions as JSON array
    private String questions; // e.g. [{"question": "...", "options": [...], "correct": 0}]

    private Integer timeLimit; // minutes

    private Integer totalQuestions;

    private Double passThreshold; // e.g. 60.00 (%)

    private Long lectureId; // Optional - linked to lecture

    @Column(nullable = false)
    private Long classId; // Required - quiz belongs to a class

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}