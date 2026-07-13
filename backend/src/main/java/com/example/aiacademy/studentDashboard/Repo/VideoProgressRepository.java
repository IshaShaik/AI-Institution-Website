package com.example.aiacademy.studentDashboard.Repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.studentDashboard.java.VideoProgress;

public interface VideoProgressRepository
        extends JpaRepository<VideoProgress, Long> {

    long countByUserIdAndCompletedTrue(Long userId);

    boolean existsByUserIdAndVideo_Id(Long userId, Long videoId);
    // Ye line add karein taaki controller error hat jaye
List<VideoProgress> findByUserIdAndCompletedTrue(Long userId);
List<VideoProgress>
    findByUserIdAndCompletedTrueAndCompletedDateBetween(
        Long userId,
        LocalDate startDate,
        LocalDate endDate
    );
}

