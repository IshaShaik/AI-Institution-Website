package com.example.aiacademy.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.aiacademy.model.javafiles.Mcq;
import com.example.aiacademy.model.javafiles.Topic;
import com.example.aiacademy.model.javafiles.Video;
import com.example.aiacademy.repo.McqRepository;
import com.example.aiacademy.repo.TopicRepository;
import com.example.aiacademy.repo.VideoRepository;
import com.example.aiacademy.service.FileStorageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/admin/videos")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoRepository videoRepo;

    @Autowired
    private McqRepository mcqRepo;

    @Autowired
    private TopicRepository topicRepo;

    @Autowired
    private FileStorageService storage;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> uploadVideo(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Long topicId,
            @RequestParam MultipartFile video,
            @RequestParam MultipartFile thumbnail,
            @RequestParam(required = false) String mcqsJson
    ) {
        try {
            Topic topic = topicRepo.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));

            Video v = new Video();
            v.setTitle(title);
            v.setDescription(description);
            v.setTopic(topic);
            v.setVideoPath(storage.saveVideo(video));
            v.setThumbnailPath(storage.saveThumb(thumbnail));

            Video savedVideo = videoRepo.save(v);

            // MCQs JSON parse & save
            if(mcqsJson != null && !mcqsJson.isBlank()){
                List<Mcq> mcqs = objectMapper.readValue(mcqsJson, new TypeReference<List<Mcq>>(){});
                for(Mcq m : mcqs){
                    m.setVideo(savedVideo);
                    mcqRepo.save(m);
                }
            }

            return ResponseEntity.ok(savedVideo);

        } catch(IOException ioe){
            ioe.printStackTrace();
            return ResponseEntity.status(500).body("File save error: " + ioe.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Video upload failed: " + e.getMessage());
        }
    }
}
