package com.Edupulse.QuizService.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClassResponse {
    private Long id;
    private String name;
    private String description;
    private Long gradeId;
    private String gradeName;
    private Long lecturerId;
    private String lecturerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;          // ← Change to String (e.g. "ACTIVE")
    private int lectureCount;
}