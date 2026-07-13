package com.example.aiacademy.studentDashboard.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.studentDashboard.java.Questions;

public interface QuestionRepository extends JpaRepository<Questions, Long> {

    List<Questions> findByLevelIdAndPartIdAndModuleIdAndQuestionSet(
        Long levelId,
        Long partId,
        Long moduleId,
        String questionSet
    );
}
