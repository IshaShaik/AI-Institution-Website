package com.example.aiacademy.studentDashboard.java;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class StudentProgress {
    @Id
    private Long studentId;
    private int correctMcq;   // Kitne sahi (Max 300)
    private int attemptedMcq; // Kitne total attempt kiye

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public int getCorrectMcq() { return correctMcq; }
    public void setCorrectMcq(int correctMcq) { this.correctMcq = correctMcq; }
    public int getAttemptedMcq() { return attemptedMcq; }
    public void setAttemptedMcq(int attemptedMcq) { this.attemptedMcq = attemptedMcq; }
}