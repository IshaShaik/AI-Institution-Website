package com.example.aiacademy.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.DTO.QuestionSetRequest;
import com.example.aiacademy.studentDashboard.Repo.QuestionRepository;
import com.example.aiacademy.studentDashboard.java.Questions;
@RestController
@RequestMapping("/api/admin/questions")
@CrossOrigin(origins = "*")
public class AdminQuestionController {

    @Autowired
    private QuestionRepository questionRepo;

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody Map<String, String> body) {
        Questions q = new Questions();
        q.setQuestionType(body.get("questionType"));
        q.setQuestionText(body.get("questionText"));
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
        questionRepo.save(q);
        return ResponseEntity.ok(Map.of("message", "Question Added Successfully"));
    }

    @PostMapping("/bulk-add") // ✅ fixed mapping
    public ResponseEntity<?> bulkAdd(@RequestBody QuestionSetRequest req) {
        for (Questions q : req.getQuestions()) {
            q.setLevelId(req.getLevelId());
            q.setPartId(req.getPartId());
            q.setModuleId(req.getModuleId());
            q.setCollegeName(req.getCollegeName());
            q.setStudentClass(req.getStudentClass());
            q.setSection(req.getSection());
            q.setQuestionSet(req.getQuestionSet());
            questionRepo.save(q);
        }
        return ResponseEntity.ok(
            java.util.Collections.singletonMap("message", "Questions saved successfully")
        );
    }
}
