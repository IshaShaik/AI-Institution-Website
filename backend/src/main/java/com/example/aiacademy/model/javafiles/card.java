package com.example.aiacademy.model.javafiles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "assessment_cards")
public class card {
    @Id
    private Long id = 1L; // Isse default ID 1 rahegi

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "part_name")
    private String partName;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "topic_name")
    private String topicName;

    @Column(name = "total_questions")
    private Integer totalQuestions;

    @Column(name = "time_limit")
    private String timeLimit;

    @Column(name = "passing_score")
    private String passingScore;

    // ... Getters and Setters ...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(String passingScore) {
        this.passingScore = passingScore;
    }
}