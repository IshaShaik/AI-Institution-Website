package com.example.aiacademy.studentDashboard.java;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_answers")
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long attemptId;
    private Long questionId;

    @Column(columnDefinition = "TEXT")
    private String submittedAnswer;

    private int marksAwarded;
    
    // ✅ 1. Add Status (CORRECT, WRONG, PARTIAL)
    private String status; 

    // ✅ 2. Add Question Total Marks (Context ke liye)
    private int questionTotalMarks;

    @Column(columnDefinition = "TEXT")
    private String aiFeedback;

    // --- GETTERS & SETTERS ---

    public Long getId() { return id; } // ID ka getter zaroori hai

    public Long getAttemptId() { return attemptId; }
    public void setAttemptId(Long attemptId) { this.attemptId = attemptId; }

    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getSubmittedAnswer() { return submittedAnswer; }
    public void setSubmittedAnswer(String submittedAnswer) { this.submittedAnswer = submittedAnswer; }

    public int getMarksAwarded() { return marksAwarded; }
    public void setMarksAwarded(int marksAwarded) { this.marksAwarded = marksAwarded; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getQuestionTotalMarks() { return questionTotalMarks; }
    public void setQuestionTotalMarks(int questionTotalMarks) { this.questionTotalMarks = questionTotalMarks; }

    public String getAiFeedback() { return aiFeedback; }
    public void setAiFeedback(String aiFeedback) { this.aiFeedback = aiFeedback; }
}