package com.example.aiacademy.studentDashboard.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.studentDashboard.service.DashboardService;
import com.example.aiacademy.studentDashboard.service.McqProgressService;
import com.example.aiacademy.studentDashboard.service.SyllabusProgressService;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired private SyllabusProgressService syllabusService;
    @Autowired private McqProgressService mcqService;
    @Autowired private DashboardService dashboardService;

    // Syllabus Coverage Score
    @GetMapping("/syllabus/{userId}")
    public double getSyllabus(@PathVariable Long userId) {
        return syllabusService.getSyllabusPercent(userId);
    }

    // Assessment Score (Naya Method)
    @GetMapping("/mcq-progress/{userId}")
    public double getMcqProgress(@PathVariable Long userId) {
        return mcqService.getAssessmentProgress(userId);
    }

    // Smart Goal
    @GetMapping("/smart-goal/{userId}")
    public ResponseEntity<?> getSmartGoal(@PathVariable Long userId) {
        String message = syllabusService.getSmartGoalMessage(userId);
        Map<String, String> response = new HashMap<>();
        response.put("goalMessage", message != null ? message : "No goals found");
        return ResponseEntity.ok(response);
    }

    // Monthly Graph
    @GetMapping("/monthly-learning/{userId}")
    public ResponseEntity<?> getMonthlyLearning(@PathVariable Long userId) {
        return ResponseEntity.ok(dashboardService.getMonthlyLearning(userId));
    }
}