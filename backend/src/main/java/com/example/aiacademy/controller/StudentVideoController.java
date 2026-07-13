package com.example.aiacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.model.javafiles.Video;
import com.example.aiacademy.repo.VideoRepository;

@RestController
@RequestMapping("/student/videos")
@CrossOrigin
public class StudentVideoController {

    @Autowired
    private VideoRepository videoRepo;

    // GET VIDEOS BY TOPIC
    @GetMapping("/{topicId}")
    public List<Video> getByTopic(@PathVariable Long topicId) {
        return videoRepo.findByTopicId(topicId);
    }
}
