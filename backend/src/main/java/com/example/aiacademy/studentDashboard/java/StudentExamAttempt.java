package com.example.aiacademy.studentDashboard.java;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "student_exam_attempt",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "studentId", "levelId", "partId", "moduleId"
        })
    }
)
public class StudentExamAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long levelId;
    private Long partId;
    private Long moduleId;

    private String status; // STARTED / COMPLETED

    // ✅ Naya Column: Isme A, B, ya C save hoga
    private String questionSet; 

    private int totalMarks = 0;
    // ✅ Admin ke assigned total paper marks (2 + 5 = 7 etc.)
private int totalPossibleMarks = 0;

    private int warningCount = 0;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
// StudentExamAttempt.java mein ye add karein
private LocalDateTime resultDate; 


    @PrePersist
    public void onStart() {
        this.startedAt = LocalDateTime.now();
    }

    // ===== getters setters =====

    public Long getId() { return id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getLevelId() { return levelId; }
    public void setLevelId(Long levelId) { this.levelId = levelId; }

    public Long getPartId() { return partId; }
    public void setPartId(Long partId) { this.partId = partId; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // ✅ Getter Setter for questionSet
    public String getQuestionSet() { return questionSet; }
    public void setQuestionSet(String questionSet) { this.questionSet = questionSet; }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public int getWarningCount() { return warningCount; }
    public void setWarningCount(int warningCount) { this.warningCount = warningCount; }
// Getter aur Setter
public LocalDateTime getResultDate() { return resultDate; }
public void setResultDate(LocalDateTime resultDate) { this.resultDate = resultDate; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public int getTotalPossibleMarks() {
        return totalPossibleMarks;
    }

    public void setTotalPossibleMarks(int totalPossibleMarks) {
        this.totalPossibleMarks = totalPossibleMarks;
    }
}