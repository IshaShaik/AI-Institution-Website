package com.example.aiacademy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.studentDashboard.Repo.ExamPasswordRepository;
import com.example.aiacademy.studentDashboard.java.ExamPassword;

@RestController
@RequestMapping("/api/admin/exam-password")
@CrossOrigin(origins = "*")
public class ExamPasswordController {

    @Autowired
    private ExamPasswordRepository repo;

    @PostMapping("/save")
    public ResponseEntity<?> savePassword(@RequestBody Map<String, String> body) {

        Long levelId = Long.parseLong(body.get("levelId"));
        Long partId = Long.parseLong(body.get("partId"));
        Long moduleId = Long.parseLong(body.get("moduleId"));
        String password = body.get("password");

        // old password deactivate
        repo.findByLevelIdAndPartIdAndModuleIdAndActiveTrue(levelId, partId, moduleId)
                .ifPresent(p -> {
                    p.setActive(false);
                    repo.save(p);
                });

        ExamPassword ep = new ExamPassword();
        ep.setLevelId(levelId);
        ep.setPartId(partId);
        ep.setModuleId(moduleId);
        ep.setPassword(password);

        repo.save(ep);

        return ResponseEntity.ok(Map.of("message", "Password saved successfully"));
    }
    @PostMapping("/verify")
public ResponseEntity<?> verifyPassword(@RequestBody Map<String, String> body) {

    String password = body.get("password");

    // active password find karo
    ExamPassword ep = repo.findAll().stream()
            .filter(p -> p.isActive() && p.getPassword().equals(password))
            .findFirst()
            .orElse(null);

    if (ep == null) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("message", "Invalid Password"));
    }

    // password match ho gaya
    return ResponseEntity.ok(
            Map.of(
                    "message", "Password Matched",
                    "levelId", ep.getLevelId(),
                    "partId", ep.getPartId(),
                    "moduleId", ep.getModuleId()
            )
    );
}

}
