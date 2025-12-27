package com.Edupulse.QuizService.controller;

import com.Edupulse.QuizService.model.Quiz;
import com.Edupulse.QuizService.model.dto.*;
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
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})

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


    // Get quizzes for a lecture (lecturer only)
    @GetMapping("/lecture/{lectureId}")
    @PreAuthorize("hasRole('LECTURER')or hasRole('STUDENT')")
    public ResponseEntity<List<Quiz>> getQuizzesForLecture(
            @PathVariable Long lectureId,
            @RequestHeader("X-User-Id") Long lecturerId) {

        List<Quiz> quizzes = quizService.getQuizzesByLecture(lectureId, lecturerId);
        return ResponseEntity.ok(quizzes);
    }



    // Get quiz result for student (per quiz)
    // Get quiz result for student (per quiz)
    @GetMapping("/{quizId}/my-result")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<QuizResultDto> getMyQuizResult(
            @PathVariable Long quizId,
            @RequestHeader("X-User-Id") Long studentId) {

        QuizResultDto result = quizService.getQuizResultForStudent(quizId, studentId);

        if (result == null) {
            return ResponseEntity.notFound().build();  // Return 404 if not attempted
        }

        return ResponseEntity.ok(result);
    }


    // Get quiz by quizId (lecturer & student)
    @GetMapping("/{quizId}")
    @PreAuthorize("hasRole('LECTURER') or hasRole('STUDENT')")
    public ResponseEntity<Quiz> getQuizById(
            @PathVariable Long quizId,
            @RequestHeader("X-User-Id") Long userId) {

        Quiz quiz = quizService.getQuizById(quizId, userId);
        return ResponseEntity.ok(quiz);
    }


    // Get detailed quiz attempt (student only - for review)
    @GetMapping("/{quizId}/attempt-details")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<QuizAttemptDetailDto> getQuizAttemptDetails(
            @PathVariable Long quizId,
            @RequestHeader("X-User-Id") Long studentId) {

        QuizAttemptDetailDto attemptDetails = quizService.getQuizAttemptDetails(quizId, studentId);
        return ResponseEntity.ok(attemptDetails);
    }

    // Get student's quiz statistics (average score and completed quiz count)
    @GetMapping("/my-statistics")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<QuizStatisticsDto> getMyQuizStatistics(
            @RequestHeader("X-User-Id") Long studentId) {

        QuizStatisticsDto statistics = quizService.getStudentQuizStatistics(studentId);
        return ResponseEntity.ok(statistics);
    }

    /**
     * Get all quiz results for a specific student (lecturer only)
     * Endpoint: GET /api/quizzes/student/{studentId}/results
     */
    @GetMapping("/student/{studentId}/results")
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<List<QuizResultDto>> getQuizResultsForStudent(
            @PathVariable Long studentId,
            @RequestHeader("X-User-Id") Long lecturerId) {

        List<QuizResultDto> results = quizService.getQuizResultsForStudent(studentId, lecturerId);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalQuizzes() {
        return ResponseEntity.ok(quizService.getTotalQuizCount());
    }
}