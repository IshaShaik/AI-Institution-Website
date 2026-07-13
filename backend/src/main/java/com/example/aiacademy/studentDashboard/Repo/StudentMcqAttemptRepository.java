package com.example.aiacademy.studentDashboard.Repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.studentDashboard.java.StudentMcqAttempt;

public interface StudentMcqAttemptRepository extends JpaRepository<StudentMcqAttempt, Long> {
    // Ye method check karega ki bache ne pehle ye question kiya hai ya nahi
    boolean existsByStudentIdAndMcqId(Long studentId, Long mcqId);
}