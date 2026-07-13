package com.example.aiacademy.studentDashboard.java;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_passwords")
public class ExamPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long levelId;
    private Long partId;
    private Long moduleId;

    private String password;

    private boolean active = true;
    // ExamPassword.java ke andar fields ke saath add karein
private java.time.LocalDateTime resultDate;



    // getters setters
    public Long getId() {
        return id;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // Getters and Setters add karein
public java.time.LocalDateTime getResultDate() {
    return resultDate;
}

public void setResultDate(java.time.LocalDateTime resultDate) {
    this.resultDate = resultDate;
}

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
