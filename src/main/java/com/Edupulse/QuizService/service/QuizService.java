package com.Edupulse.QuizService.service;

import com.Edupulse.QuizService.model.Quiz;
import com.Edupulse.QuizService.model.QuizResponse;
import com.Edupulse.QuizService.model.dto.*;
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
        // 1. Validate student
        UserResponse student = userServiceClient.validateStudent(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }

        // 2. Find quiz
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        // 3. Prevent resubmission
        if (quizResponseRepository.findByStudentIdAndQuizId(studentId, quiz.getId()).isPresent()) {
            throw new IllegalStateException("Quiz already submitted");
        }

        // 4. Calculate score
        double score = calculateScore(quiz.getQuestions(), request.getResponses());

        // 5. Build & SAVE entity first → JPA/DB sets submittedAt
        QuizResponse response = QuizResponse.builder()
                .studentId(studentId)
                .quizId(quiz.getId())
                .responses(request.getResponses())
                .score(score)
                .timeTaken(request.getTimeTaken())
                .build();

        response = quizResponseRepository.save(response);  // ← Save FIRST

        // 6. Now build DTO with real submittedAt (after save)
        return QuizResultDto.builder()
                .quizId(quiz.getId())
                .title(quiz.getTitle())
                .studentId(studentId)
                .studentName(student.getFullName())  // ← Real name from UserService!
                .score(score)
                .passed(score >= quiz.getPassThreshold() ? "PASS" : "FAIL")
                .timeTaken(request.getTimeTaken())
                .submittedAt(response.getSubmittedAt())  // ← Now it's set!
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
                .map(r -> {
                    // Fetch student name via Feign
                    String studentName = "Unknown Student";
                    try {
                        UserResponse student = userServiceClient.validateStudent(r.getStudentId());
                        studentName = student.getFullName();
                    } catch (Exception e) {
                        // Log if needed
                    }

                    return QuizResultDto.builder()
                            .quizId(quizId)
                            .title(quiz.getTitle())
                            .studentId(r.getStudentId())
                            .studentName(studentName)  // ← Real name!
                            .score(r.getScore())
                            .passed(r.getScore() >= quiz.getPassThreshold() ? "PASS" : "FAIL")
                            .timeTaken(r.getTimeTaken())
                            .submittedAt(r.getSubmittedAt())  // ← Already set from DB
                            .build();
                })
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

    // Get all quizzes for a lecture
    public List<Quiz> getQuizzesByLecture(Long lectureId, Long lecturerId) {

//        // Optional but STRONGLY recommended:
//        // Validate lecturer exists
//        UserResponse lecturer = userServiceClient.validateLecturer(lecturerId);
//        if (lecturer == null) {
//            throw new IllegalArgumentException("Lecturer not found");
//        }

        return quizRepository.findByLectureId(lectureId);
    }


    // Get quiz result for a student (per quiz)
    public QuizResultDto getQuizResultForStudent(Long quizId, Long studentId) {

        // Validate student
        UserResponse student = userServiceClient.validateStudent(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }

        // Find quiz
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        // Find student's submission
        QuizResponse response = quizResponseRepository
                .findByStudentIdAndQuizId(studentId, quizId)
                .orElseThrow(() -> new IllegalStateException("Quiz not attempted"));

        return QuizResultDto.builder()
                .quizId(quiz.getId())
                .title(quiz.getTitle())
                .studentId(studentId)
                .studentName(student.getFullName())
                .score(response.getScore())
                .passed(response.getScore() >= quiz.getPassThreshold() ? "PASS" : "FAIL")
                .timeTaken(response.getTimeTaken())
                .submittedAt(response.getSubmittedAt())
                .build();
    }



    public Quiz getQuizById(Long quizId, Long userId) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        // Optional but RECOMMENDED: validate access via class
        ClassResponse classInfo = classServiceClient.getClassById(quiz.getClassId());
        if (classInfo == null) {
            throw new IllegalArgumentException("Class not found");
        }

        // Lecturer OR student can view quiz
        // (If you want stricter checks later, add enrollment validation here)

        return quiz;
    }

}