package com.example.aiacademy.studentDashboard.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aiacademy.studentDashboard.Repo.VideoProgressRepository;
import com.example.aiacademy.studentDashboard.java.MonthlyLearningResponse;
import com.example.aiacademy.studentDashboard.java.VideoProgress;

@Service
public class DashboardService {

    @Autowired
    private VideoProgressRepository progressRepo;

    // ✅ Monthly Learning Activity
   public MonthlyLearningResponse getMonthlyLearning(Long userId) {
    LocalDate startDate = LocalDate.now().withDayOfMonth(1);
    LocalDate endDate = LocalDate.now();

    // Query result handle karein
    List<VideoProgress> completedList = progressRepo.findByUserIdAndCompletedTrueAndCompletedDateBetween(
            userId, startDate, endDate
    );

    int totalVideos = (completedList != null) ? completedList.size() : 0;
    int expectedVideos = 8;
    String status = (totalVideos >= expectedVideos) ? (totalVideos > expectedVideos ? "ABOVE" : "ON_TRACK") : "BELOW";

    return new MonthlyLearningResponse(totalVideos, expectedVideos, status);
}
}
