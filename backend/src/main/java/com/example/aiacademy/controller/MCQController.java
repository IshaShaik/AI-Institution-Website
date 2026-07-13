package com.example.aiacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.model.javafiles.Mcq;
import com.example.aiacademy.repo.McqRepository;
import com.example.aiacademy.repo.VideoRepository;

@RestController
@RequestMapping("/admin/mcq")
@CrossOrigin
public class MCQController {

    @Autowired
    private McqRepository mcqRepo;

    @Autowired
    private VideoRepository videoRepo;

    // CREATE MCQ
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Mcq m) {
        try {
            Mcq saved = mcqRepo.save(m);
            return ResponseEntity.ok(saved);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("MCQ creation failed: " + e.getMessage());
        }
    }

    // GET MCQs by Video
    @GetMapping("/video/{id}")
    public ResponseEntity<?> byVideo(@PathVariable Long id){
        try {
            List<Mcq> list = mcqRepo.findAll().stream()
                    .filter(m -> m.getVideo().getId().equals(id))
                    .toList();
            return ResponseEntity.ok(list);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching MCQs: " + e.getMessage());
        }
    }

    // UPDATE MCQ
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Mcq m){
        try {
            Mcq db = mcqRepo.findById(id).orElseThrow();
            db.setQuestion(m.getQuestion());
            db.setOptionA(m.getOptionA());
            db.setOptionB(m.getOptionB());
            db.setOptionC(m.getOptionC());
            db.setOptionD(m.getOptionD());
            db.setCorrectOption(m.getCorrectOption());
            db.setExplanation(m.getExplanation());
            Mcq saved = mcqRepo.save(db);
            return ResponseEntity.ok(saved);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("MCQ update failed: " + e.getMessage());
        }
    }

    // DELETE MCQ
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            mcqRepo.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("MCQ delete failed: " + e.getMessage());
        }
    }

    public VideoRepository getVideoRepo() {
        return videoRepo;
    }

    public void setVideoRepo(VideoRepository videoRepo) {
        this.videoRepo = videoRepo;
    }
}
