package com.Edupulse.QuizService.controller;

import com.Edupulse.QuizService.model.Quiz;
import com.Edupulse.QuizService.model.dto.QuizRequest;
import com.Edupulse.QuizService.model.dto.QuizResponseRequest;
import com.Edupulse.QuizService.model.dto.QuizResultDto;
import com.Edupulse.QuizService.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    // Create quiz (lecturer only)
    @PostMapping
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<Quiz> createQuiz(
            @Valid @RequestBody QuizRequest request,
            @RequestHeader("X-User-Id") Long lecturerId) {

        Quiz quiz = quizService.createQuiz(request, lecturerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
    }

    // Submit quiz answers (student only)
    @PostMapping("/submit")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<QuizResultDto> submitQuiz(
            @Valid @RequestBody QuizResponseRequest request,
            @RequestHeader("X-User-Id") Long studentId) {

        QuizResultDto result = quizService.submitQuiz(studentId, request);
        return ResponseEntity.ok(result);
    }

    // Get quiz results (lecturer only - monitoring)
    @GetMapping("/{quizId}/results")
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<List<QuizResultDto>> getQuizResults(
            @PathVariable Long quizId,
            @RequestHeader("X-User-Id") Long lecturerId) {

        List<QuizResultDto> results = quizService.getQuizResults(quizId, lecturerId);
        return ResponseEntity.ok(results);
    }
}