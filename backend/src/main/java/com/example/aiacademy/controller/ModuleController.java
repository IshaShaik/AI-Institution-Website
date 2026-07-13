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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.DTO.ModuleRequest;
import com.example.aiacademy.model.javafiles.ModuleEntity;
import com.example.aiacademy.model.javafiles.Part;
import com.example.aiacademy.repo.ModuleRepository;
import com.example.aiacademy.repo.PartRepository;
@RestController
@RequestMapping("/admin/modules")
@CrossOrigin
public class ModuleController {

    @Autowired private ModuleRepository repo;
    @Autowired private PartRepository partRepo;

@PostMapping
public ResponseEntity<?> create(@RequestBody ModuleRequest request) {
    try {
        System.out.println("Module request: " + request.getName() + ", partId=" + request.getPartId());
        Part part = partRepo.findById(request.getPartId())
                .orElseThrow(() -> new RuntimeException("Part not found"));
        ModuleEntity m = new ModuleEntity();
        m.setName(request.getName());
        m.setPart(part);
        ModuleEntity saved = repo.save(m);
        System.out.println("Module saved with ID: " + saved.getId());
        return ResponseEntity.ok(saved);
    } catch(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(e.getMessage());
    }
}


  @GetMapping
public List<ModuleEntity> byPart(@RequestParam(required = false) Long partId) {
    if (partId == null) return List.of();
    return repo.findByPartId(partId);
}



    @PutMapping("/{id}")
    public ModuleEntity update(@PathVariable Long id, @RequestBody ModuleEntity m) {
        ModuleEntity db = repo.findById(id).orElseThrow();
        db.setName(m.getName());
        return repo.save(db);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
