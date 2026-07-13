package com.example.aiacademy.studentDashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.model.javafiles.Video;
import com.example.aiacademy.repo.VideoRepository;
import com.example.aiacademy.studentDashboard.Repo.VideoProgressRepository;
import com.example.aiacademy.studentDashboard.java.VideoProgress;

@RestController
@RequestMapping("/api/student/video")
@CrossOrigin
public class VideoCompleteController {

    @Autowired
    private VideoProgressRepository progressRepo;

    @Autowired
    private VideoRepository videoRepo;
  @GetMapping("/completed-videos/{userId}")
public List<Long> getCompletedVideoIds(@PathVariable Long userId) {
    // Sirf IDs return karein taaki 500 Error na aaye
    return progressRepo.findByUserIdAndCompletedTrue(userId)
                       .stream()
                       .map(vp -> vp.getVideo().getId())
                       .toList();
}

    @PostMapping("/complete")
    public String completeVideo(
            @RequestParam Long userId,
            @RequestParam Long videoId
    ) {

        if (progressRepo.existsByUserIdAndVideo_Id(userId, videoId)) {
            return "Already completed";
        }

        Video video = videoRepo.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        VideoProgress vp = new VideoProgress();
        vp.setUserId(userId);
        vp.setVideo(video);
        vp.setCompleted(true);

        progressRepo.save(vp);
        return "Video completed";
    }
}
