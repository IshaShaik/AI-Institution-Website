package com.example.aiacademy.studentDashboard.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.studentDashboard.java.StudentExamAttempt;

public interface StudentExamAttemptRepository extends JpaRepository<StudentExamAttempt, Long> {

    Optional<StudentExamAttempt> findByStudentIdAndLevelIdAndPartIdAndModuleId(
        Long studentId, Long levelId, Long partId, Long moduleId
    );

    // ✅ Ye naya method add karein Set allocation ke liye
    long countByLevelIdAndPartIdAndModuleId(Long levelId, Long partId, Long moduleId);
}