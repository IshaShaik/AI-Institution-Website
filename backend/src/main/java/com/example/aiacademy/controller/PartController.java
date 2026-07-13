package com.example.aiacademy.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.DTO.PartRequest;
import com.example.aiacademy.model.javafiles.Part;
import com.example.aiacademy.repo.LevelRepository;
import com.example.aiacademy.repo.PartRepository;
@RestController
@RequestMapping("/admin/parts")
@CrossOrigin
public class PartController {

    @Autowired private PartRepository partRepo;
    @Autowired private LevelRepository levelRepo;

    @PostMapping
public Part create(@RequestBody PartRequest request) {
    Part p = new Part();
    p.setName(request.getName());
    p.setLevel(levelRepo.findById(request.getLevelId()).orElseThrow());
    return partRepo.save(p);
}
    @GetMapping
    public List<Part> byLevel(@RequestParam Long levelId) {
        return partRepo.findAll().stream()
                .filter(p -> p.getLevel().getId().equals(levelId))
                .toList();
    }

    @PutMapping("/{id}")
    public Part update(@PathVariable Long id, @RequestBody Part p) {
        Part db = partRepo.findById(id).orElseThrow();
        db.setName(p.getName());
        return partRepo.save(db);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        partRepo.deleteById(id);
    }
}
