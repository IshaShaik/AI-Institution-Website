package com.example.aiacademy.studentDashboard.java;

import com.example.aiacademy.model.javafiles.Topic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_type")
private String questionType;
// MCQ / CODING / NORMAL
@ManyToOne
@JoinColumn(name = "topic_id")
private Topic topic;

    @Column(length = 5000)
    private String questionText;

    @Column(length = 3000)
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    @Column(length = 3000)
    private String correctAnswer;

    private int marks;

    // -------- Mapping IDs (already existing tables) --------
    private Long levelId;
    private Long partId;
    private Long moduleId;

    // -------- Student filter --------
    private String collegeName;
    private String studentClass;
    private String section;
  private String questionSet; // A / B / C
    // -------- getters & setters --------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestionType() { return questionType; }
    public void setQuestionType(String questionType) { this.questionType = questionType; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getOptionA() { return optionA; }
    public void setOptionA(String optionA) { this.optionA = optionA; }

    public String getOptionB() { return optionB; }
    public void setOptionB(String optionB) { this.optionB = optionB; }

    public String getOptionC() { return optionC; }
    public void setOptionC(String optionC) { this.optionC = optionC; }

    public String getOptionD() { return optionD; }
    public void setOptionD(String optionD) { this.optionD = optionD; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }

    public Long getLevelId() { return levelId; }
    public void setLevelId(Long levelId) { this.levelId = levelId; }

    public Long getPartId() { return partId; }
    public void setPartId(Long partId) { this.partId = partId; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public String getCollegeName() { return collegeName; }
    public void setCollegeName(String collegeName) { this.collegeName = collegeName; }

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(String questionSet) {
        this.questionSet = questionSet;
    }
    public Topic getTopic() {
    return topic;
}

public void setTopic(Topic topic) {
    this.topic = topic;
}
}
