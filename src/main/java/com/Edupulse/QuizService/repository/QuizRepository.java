package com.Edupulse.QuizService.repository;

import com.Edupulse.QuizService.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByClassId(Long classId);
    List<Quiz> findByLectureId(Long lectureId);
}