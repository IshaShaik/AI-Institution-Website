package com.example.aiacademy.studentDashboard.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aiacademy.model.javafiles.Mcq;
import com.example.aiacademy.repo.McqRepository;
import com.example.aiacademy.studentDashboard.Repo.StudentMcqAttemptRepository;
import com.example.aiacademy.studentDashboard.Repo.StudentProgressRepository;
import com.example.aiacademy.studentDashboard.java.StudentMcqAttempt;
import com.example.aiacademy.studentDashboard.java.StudentProgress;

@Service
public class McqProgressService {
    @Autowired private McqRepository mcqRepo;
    @Autowired private StudentMcqAttemptRepository attemptRepo;
    @Autowired private StudentProgressRepository progressRepo;

    // Dashboard ke liye current percentage nikalne wala method
    public double getAssessmentProgress(Long studentId) {
        return progressRepo.findById(studentId)
                .map(p -> (p.getCorrectMcq() / 300.0) * 100)
                .orElse(0.0);
    }

    // MCQ Submit karne wala method
    public Map<String, Object> submitMcqAnswer(Long studentId, Long mcqId, String selectedOption) {
        if (attemptRepo.existsByStudentIdAndMcqId(studentId, mcqId)) {
            throw new RuntimeException("Already Attempted");
        }

        Mcq mcq = mcqRepo.findById(mcqId).orElseThrow();
        boolean isCorrect = mcq.getCorrectOption().equalsIgnoreCase(selectedOption);

        StudentMcqAttempt attempt = new StudentMcqAttempt();
        attempt.setStudentId(studentId);
        attempt.setMcq(mcq);
        attempt.setCorrect(isCorrect);
        attemptRepo.save(attempt);

        StudentProgress progress = progressRepo.findById(studentId).orElse(new StudentProgress());
        if (progress.getStudentId() == null) progress.setStudentId(studentId);

        progress.setAttemptedMcq(progress.getAttemptedMcq() + 1);
        if (isCorrect) {
            progress.setCorrectMcq(Math.min(300, progress.getCorrectMcq() + 1));
        }
        progressRepo.save(progress);

        Map<String, Object> response = new HashMap<>();
        response.put("isCorrect", isCorrect);
        response.put("assessmentScore", (progress.getCorrectMcq() / 300.0) * 100);
        return response;
    }
}