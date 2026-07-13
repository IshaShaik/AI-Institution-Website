package com.example.aiacademy.studentDashboard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.model.javafiles.Topic;
import com.example.aiacademy.repo.TopicRepository;
import com.example.aiacademy.studentDashboard.Repo.QuestionRepository;
import com.example.aiacademy.studentDashboard.java.Questions;

@RestController
@RequestMapping("/api/student/questions")
@CrossOrigin("*")
public class StudentQuestionController {

    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
private TopicRepository topicRepository;

    @PostMapping("/load")
    public ResponseEntity<?> loadQuestions(@RequestBody Map<String, String> body) {

        Long levelId = Long.parseLong(body.get("levelId"));
        Long partId = Long.parseLong(body.get("partId"));
        Long moduleId = Long.parseLong(body.get("moduleId"));
        String questionSet = body.get("questionSet"); // A / B / C

        var questions = questionRepo
                .findByLevelIdAndPartIdAndModuleIdAndQuestionSet(
                        levelId, partId, moduleId, questionSet
                )
                .stream()
                .map(q -> Map.of(
                        "id", q.getId(),
                        "type", q.getQuestionType(),
                        "question", q.getQuestionText(),
                        "optionA", q.getOptionA(),
                        "optionB", q.getOptionB(),
                        "optionC", q.getOptionC(),
                        "optionD", q.getOptionD(),
                        "marks", q.getMarks()
                        // ❌ correctAnswer intentionally NOT sent
                ))
                .toList();

        return ResponseEntity.ok(questions);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody Map<String, String> body) {

        Long topicId = Long.parseLong(body.get("topicId"));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        Questions q = new Questions();

        q.setQuestionText(body.get("questionText"));
        q.setQuestionType(body.get("questionType"));

        q.setOptionA(body.get("optionA"));
        q.setOptionB(body.get("optionB"));
        q.setOptionC(body.get("optionC"));
        q.setOptionD(body.get("optionD"));

        q.setCorrectAnswer(body.get("correctAnswer"));
        q.setMarks(Integer.parseInt(body.get("marks")));

        q.setLevelId(Long.parseLong(body.get("levelId")));
        q.setPartId(Long.parseLong(body.get("partId")));
        q.setModuleId(Long.parseLong(body.get("moduleId")));

        q.setCollegeName(body.get("collegeName"));
        q.setStudentClass(body.get("studentClass"));
        q.setSection(body.get("section"));

        q.setQuestionSet(body.get("questionSet"));

        q.setTopic(topic);

        questionRepo.save(q);

        return ResponseEntity.ok("Question Added Successfully");
    }
}
