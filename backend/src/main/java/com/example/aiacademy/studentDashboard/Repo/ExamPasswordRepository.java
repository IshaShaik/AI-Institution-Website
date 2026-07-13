package com.example.aiacademy.studentDashboard.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.studentDashboard.java.ExamPassword;

public interface ExamPasswordRepository extends JpaRepository<ExamPassword, Long> {

    // Ye pehle se hai
    Optional<ExamPassword> findByLevelIdAndPartIdAndModuleIdAndActiveTrue(
            Long levelId, Long partId, Long moduleId);

    // 👈 Ye Nayi Line add karein (Controller ke liye)
    Optional<ExamPassword> findByLevelIdAndPartIdAndModuleId(Long levelId, Long partId, Long moduleId);

    Optional<ExamPassword> findByPasswordAndActiveTrue(String password);
}