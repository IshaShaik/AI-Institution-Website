package com.example.aiacademy.studentDashboard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.studentDashboard.service.McqProgressService;

@RestController
@RequestMapping("/api/student/mcq")
@CrossOrigin(origins = "*") // Frontend block na ho isliye
public class McqDashboardController {

    @Autowired
    private McqProgressService mcqService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswer(
            @RequestParam Long userId,      
            @RequestParam Long mcqId, 
            @RequestParam String selectedOption) { 
        try {
            // Service method ko call karke DB mein save karega
            Map<String, Object> result = mcqService.submitMcqAnswer(userId, mcqId, selectedOption);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}