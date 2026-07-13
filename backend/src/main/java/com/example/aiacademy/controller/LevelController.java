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
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.DTO.LevelRequest;
import com.example.aiacademy.model.javafiles.Level;
import com.example.aiacademy.repo.LevelRepository;

@RestController
@RequestMapping("/admin/levels")
@CrossOrigin
public class LevelController {

    @Autowired
    private LevelRepository repo;

  @PostMapping
public Level create(@RequestBody LevelRequest request) {
    Level l = new Level();
    l.setName(request.getName());
    return repo.save(l);
}

    @GetMapping
    public List<Level> getAll() {
        return repo.findAll();
    }

    @PutMapping("/{id}")
    public Level update(@PathVariable Long id, @RequestBody Level l) {
        Level db = repo.findById(id).orElseThrow();
        db.setName(l.getName());
        return repo.save(db);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
