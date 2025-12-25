package com.Edupulse.QuizService.service;

import com.Edupulse.QuizService.model.Quiz;
import com.Edupulse.QuizService.model.QuizResponse;
import com.Edupulse.QuizService.model.dto.ClassResponse;
import com.Edupulse.QuizService.model.dto.QuizRequest;
import com.Edupulse.QuizService.model.dto.QuizResponseRequest;
import com.Edupulse.QuizService.model.dto.QuizResultDto;
import com.Edupulse.QuizService.repository.QuizRepository;
import com.Edupulse.QuizService.repository.QuizResponseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizResponseRepository quizResponseRepository;
    private final ClassServiceClient classServiceClient;
    private final UserServiceClient userServiceClient;

    // Create quiz (by lecturer)
    @Transactional
    public Quiz createQuiz(QuizRequest request, Long lecturerId) {
        // Validate lecturer owns the class
        ClassResponse classInfo = classServiceClient.getClassById(request.getClassId());
        if (classInfo == null || !classInfo.getLecturerId().equals(lecturerId)) {
            throw new IllegalArgumentException("Unauthorized or class not found");
        }

        Quiz quiz = Quiz.builder()
                .title(request.getTitle())
                .questions(request.getQuestions())
                .timeLimit(request.getTimeLimit())
                .totalQuestions(calculateTotalQuestions(request.getQuestions())) // custom logic
                .passThreshold(request.getPassThreshold())
                .lectureId(request.getLectureId())
                .classId(request.getClassId())
                .build();

        return quizRepository.save(quiz);
    }

    // Submit quiz answers (by student)
    @Transactional
    public QuizResultDto submitQuiz(Long studentId, QuizResponseRequest request) {
        // Validate student
        userServiceClient.validateStudent(studentId);

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        // Prevent resubmission
        if (quizResponseRepository.findByStudentIdAndQuizId(studentId, quiz.getId()).isPresent()) {
            throw new IllegalStateException("Quiz already submitted");
        }

        // Calculate score (your logic - parse questions & responses)
        double score = calculateScore(quiz.getQuestions(), request.getResponses());

        QuizResponse response = QuizResponse.builder()
                .studentId(studentId)
                .quizId(quiz.getId())
                .responses(request.getResponses())
                .score(score)
                .timeTaken(request.getTimeTaken())
                .build();

        response = quizResponseRepository.save(response);

        return QuizResultDto.builder()
                .quizId(quiz.getId())
                .title(quiz.getTitle())
                .studentId(studentId)
                .studentName("Student Name") // fetch via Feign if needed
                .score(score)
                .passed(score >= quiz.getPassThreshold() ? "PASS" : "FAIL")
                .timeTaken(request.getTimeTaken())
                .submittedAt(response.getSubmittedAt())
                .build();
    }

    // Get quiz results for monitoring (lecturer)
    public List<QuizResultDto> getQuizResults(Long quizId, Long lecturerId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        // Validate lecturer owns the class
        ClassResponse classInfo = classServiceClient.getClassById(quiz.getClassId());
        if (!classInfo.getLecturerId().equals(lecturerId)) {
            throw new IllegalArgumentException("Unauthorized");
        }

        List<QuizResponse> responses = quizResponseRepository.findByQuizId(quizId);

        return responses.stream()
                .map(r -> QuizResultDto.builder()
                        .quizId(quizId)
                        .title(quiz.getTitle())
                        .studentId(r.getStudentId())
                        .studentName("Student Name") // fetch via Feign
                        .score(r.getScore())
                        .passed(r.getScore() >= quiz.getPassThreshold() ? "PASS" : "FAIL")
                        .timeTaken(r.getTimeTaken())
                        .submittedAt(r.getSubmittedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // Simple score calculation (customize as needed)
    private double calculateScore(String quizQuestionsJson, String studentResponsesJson) {
        // Parse JSON, compare answers, return % score
        return 85.5; // placeholder
    }

    private int calculateTotalQuestions(String questionsJson) {
        // Parse JSON array length
        return 10; // placeholder
    }
}