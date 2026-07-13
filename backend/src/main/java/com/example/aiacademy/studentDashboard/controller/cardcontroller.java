package com.example.aiacademy.studentDashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.model.javafiles.card;
import com.example.aiacademy.repo.Cardrepo;

@RestController
@RequestMapping("/api/exam")
@CrossOrigin(origins = "*")
public class cardcontroller {

    @Autowired
    private Cardrepo repository;

    @PostMapping("/update")
    public ResponseEntity<?> updateExam(@RequestBody card newSetting) {
        // Hamesha ID 1 wala data nikalein, agar nahi hai to naya banayein
        card existing = repository.findById(1L).orElse(new card());

        existing.setId(1L); 
        existing.setLevelName(newSetting.getLevelName());
        existing.setPartName(newSetting.getPartName());
        existing.setModuleName(newSetting.getModuleName());
        existing.setTopicName(newSetting.getTopicName());
        existing.setTotalQuestions(newSetting.getTotalQuestions());
        existing.setTimeLimit(newSetting.getTimeLimit());
        existing.setPassingScore(newSetting.getPassingScore());

        repository.save(existing);
        return ResponseEntity.ok("Exam Updated Successfully");
    }

    @GetMapping("/current")
    public ResponseEntity<card> getCurrentExam() {
        return ResponseEntity.ok(repository.findById(1L).orElse(new card()));
    }
}