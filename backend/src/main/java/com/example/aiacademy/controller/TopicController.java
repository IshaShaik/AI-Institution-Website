package com.example.aiacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.DTO.TopicRequest;
import com.example.aiacademy.model.javafiles.Topic;
import com.example.aiacademy.repo.ModuleRepository;
import com.example.aiacademy.repo.TopicRepository;
@RestController
@RequestMapping("/admin/topics")
@CrossOrigin
public class TopicController {

    @Autowired
    private TopicRepository topicRepo;

    @Autowired
    private ModuleRepository moduleRepo;

    // CREATE TOPIC
    @PostMapping
    public Topic create(@RequestBody TopicRequest request) {

        Topic t = new Topic();
        t.setName(request.getName());
        t.setModule(
            moduleRepo.findById(request.getModuleId())
                      .orElseThrow(() -> new RuntimeException("Module not found"))
        );

        return topicRepo.save(t);
    }

    // GET TOPICS BY MODULE
    @GetMapping
    public List<Topic> byModule(@RequestParam Long moduleId) {
        return topicRepo.findAll()
                .stream()
                .filter(t -> t.getModule().getId().equals(moduleId))
                .toList();
    }
}
