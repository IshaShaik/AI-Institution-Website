package com.example.aiacademy.studentDashboard.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aiacademy.studentDashboard.java.StudentAnswer;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

    // Ek specific exam attempt ke saare answers nikalne ke liye
    List<StudentAnswer> findByAttemptId(Long attemptId);

    // Ye check karne ke liye ki kya bache ne koi specific question pehle hi answer kiya hai
    boolean existsByAttemptIdAndQuestionId(Long attemptId, Long questionId);
}