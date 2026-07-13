package com.example.aiacademy.studentDashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.aiacademy.model.javafiles.Video; // Sabhi models import karein
import com.example.aiacademy.repo.VideoRepository;
import com.example.aiacademy.studentDashboard.Repo.VideoProgressRepository;
import com.example.aiacademy.studentDashboard.java.VideoProgress;

@Service
public class SyllabusProgressService {

    private static final int TOTAL_VIDEOS = 300; 

    @Autowired
    private VideoProgressRepository progressRepo;

    @Autowired
    private VideoRepository videoRepo;

    // ✅ Syllabus Percent
    public double getSyllabusPercent(Long userId) {
        long completedVideos = progressRepo.countByUserIdAndCompletedTrue(userId);
        return Math.round((completedVideos * 10000.0) / TOTAL_VIDEOS) / 100.0;
    }

    // ✅ Next Video Recommendation
    public Video getNextVideo(Long userId) {
        List<Long> doneIds = progressRepo.findByUserIdAndCompletedTrue(userId)
                                         .stream()
                                         .map(vp -> vp.getVideo().getId())
                                         .toList();

        List<Video> allVideos = videoRepo.findAll();
        allVideos.sort((v1, v2) -> v1.getId().compareTo(v2.getId()));

        for (Video v : allVideos) {
            if (!doneIds.contains(v.getId())) return v;
        }
        return null;
    }

    // ✅ Smart Goal Message (Chain Mapping Logic)
    @Transactional(readOnly = true) // Isse Lazy Loading error solve hoga
    public String getSmartGoalMessage(Long userId) {
        List<VideoProgress> progressList = progressRepo.findByUserIdAndCompletedTrue(userId);
        
        if (progressList == null || progressList.isEmpty()) {
            return "Start Level 1 to begin your journey!";
        }

        // Latest entry fetch karein
        VideoProgress latest = progressList.get(progressList.size() - 1);
        if (latest == null || latest.getVideo() == null) {
            return "Keep learning to unlock your next goal!";
        }

        Video v = latest.getVideo();
        
        // Default values agar chain mein kuch null mile
        String lId = "1", pId = "1", mId = "1", tId = "1";

        try {
            if (v.getTopic() != null) {
                tId = String.valueOf(v.getTopic().getId());
                if (v.getTopic().getModule() != null) {
                    mId = String.valueOf(v.getTopic().getModule().getId());
                    if (v.getTopic().getModule().getPart() != null) {
                        pId = String.valueOf(v.getTopic().getModule().getPart().getId());
                        if (v.getTopic().getModule().getPart().getLevel() != null) {
                            lId = String.valueOf(v.getTopic().getModule().getPart().getLevel().getId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Agar nested objects load nahi ho paaye
            return "Goal: Continue watching " + v.getTitle();
        }

        return String.format("L%s -> P%s -> M%s -> T%s -> Video '%s' is completed.", 
                lId, pId, mId, tId, v.getTitle());
    }
}