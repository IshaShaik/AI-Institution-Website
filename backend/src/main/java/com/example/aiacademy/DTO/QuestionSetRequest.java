package com.example.aiacademy.DTO;
import java.util.List;

import com.example.aiacademy.studentDashboard.java.Questions;
public class QuestionSetRequest {

    private Long levelId;
    private Long partId;
    private Long moduleId;

    private String collegeName;
    private String studentClass;
    private String section;
    private String questionSet;

    private List<Questions> questions;

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

    public String getQuestionSet() { return questionSet; }
    public void setQuestionSet(String questionSet) { this.questionSet = questionSet; }

    public List<Questions> getQuestions() { return questions; }
    public void setQuestions(List<Questions> questions) { this.questions = questions; }
}