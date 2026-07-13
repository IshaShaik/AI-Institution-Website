package com.example.aiacademy.studentDashboard.java;

import java.time.LocalDate;

import com.example.aiacademy.model.javafiles.Video;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "video_progress",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "video_id"})
    }
)
public class VideoProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

   @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "video_id", nullable = false)
private Video video;


    @Column(nullable = false)
    private boolean completed;
    @Column(nullable = false)
    private LocalDate completedDate;


    // ===== getters & setters =====
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }
}
