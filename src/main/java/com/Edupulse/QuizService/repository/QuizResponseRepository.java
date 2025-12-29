package com.Edupulse.QuizService.repository;

import com.Edupulse.QuizService.model.QuizResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResponseRepository extends JpaRepository<QuizResponse, Long> {
    Optional<QuizResponse> findByStudentIdAndQuizId(Long studentId, Long quizId);

    List<QuizResponse> findByQuizId(Long quizId);

    @Query("SELECT AVG(qr.score) FROM QuizResponse qr WHERE qr.quizId = :quizId")
    Double getAverageScoreByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT COUNT(qr) FROM QuizResponse qr WHERE qr.quizId = :quizId AND qr.score >= :threshold")
    long countPassedByQuizId(@Param("quizId") Long quizId, @Param("threshold") Double threshold);

    List<QuizResponse> findByStudentId(Long studentId);
}