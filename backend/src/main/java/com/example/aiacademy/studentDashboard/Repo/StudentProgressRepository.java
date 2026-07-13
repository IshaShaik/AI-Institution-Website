package com.example.aiacademy.studentDashboard.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.studentDashboard.java.StudentProgress;

public interface StudentProgressRepository extends JpaRepository<StudentProgress, Long> {
}