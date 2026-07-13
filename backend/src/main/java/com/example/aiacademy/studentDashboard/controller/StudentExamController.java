package com.example.aiacademy.studentDashboard.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.studentDashboard.Repo.ExamPasswordRepository;
import com.example.aiacademy.studentDashboard.Repo.QuestionRepository;
import com.example.aiacademy.studentDashboard.Repo.StudentAnswerRepository;
import com.example.aiacademy.studentDashboard.Repo.StudentExamAttemptRepository;
import com.example.aiacademy.studentDashboard.java.ExamPassword;
import com.example.aiacademy.studentDashboard.java.StudentAnswer;
import com.example.aiacademy.studentDashboard.java.StudentExamAttempt;
import com.example.aiacademy.studentDashboard.service.ExamGradingService;

@RestController
@RequestMapping("/api/student/exam")
@CrossOrigin(origins = "*")
public class StudentExamController {
  
    @Autowired
private StudentAnswerRepository answerRepo; // Isse inject karein
    @Autowired
    private ExamPasswordRepository passwordRepo;

    @Autowired
    private StudentExamAttemptRepository attemptRepo;
@Autowired
    private QuestionRepository questionRepository; // 👈 Isse add karein correct answer nikalne ke liye

    @Autowired
    private ExamGradingService gradingService;
    /**
     * STEP 1: password verify
     * STEP 2: already exam diya ya nahi
     * STEP 3: new attempt create
     */
 @PostMapping("/start")
public ResponseEntity<?> startExam(@RequestBody Map<String, String> body) {
    try {
        String sIdRaw = body.get("studentId");
        String password = body.get("password");

        if (sIdRaw == null || sIdRaw.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Student ID is missing"));
        }
        
        Long studentId = Long.parseLong(sIdRaw);

        // 1. Check Password
        ExamPassword ep = passwordRepo.findByPasswordAndActiveTrue(password).orElse(null);
        if (ep == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "invalid password try again"));
        }

        Long lId = ep.getLevelId();
        Long pId = ep.getPartId();
        Long mId = ep.getModuleId();

        // 2. Check for Existing Attempt
        Optional<StudentExamAttempt> existing = attemptRepo.findByStudentIdAndLevelIdAndPartIdAndModuleId(studentId, lId, pId, mId);
        
        if (existing.isPresent()) {
            StudentExamAttempt oldAttempt = existing.get();
            
            // ✅ AGAR COMPLETED HAI - TABHI BLOCK KARO
            if ("COMPLETED".equalsIgnoreCase(oldAttempt.getStatus())) {
                return ResponseEntity.ok(Map.of("status", "BLOCKED", "message", "You already completed this exam"));
            }
            
            // ✅ AGAR STARTED HAI - TO WAHI PURANA DATA BHEJ DO (ALLOW KARO)
            return ResponseEntity.ok(Map.of(
                "status", "STARTED",
                "attemptId", oldAttempt.getId(),
                "set", oldAttempt.getQuestionSet(),
                "levelId", lId,
                "partId", pId,
                "moduleId", mId
            ));
        }

        // 3. New Attempt (Fresh Start)
        long totalStudents = attemptRepo.countByLevelIdAndPartIdAndModuleId(lId, pId, mId);
        String assignedSet = (totalStudents % 30 < 10) ? "A" : (totalStudents % 30 < 20 ? "B" : "C");

        StudentExamAttempt attempt = new StudentExamAttempt();
        attempt.setStudentId(studentId);
        attempt.setLevelId(lId);
        attempt.setPartId(pId);
        attempt.setModuleId(mId);
        attempt.setQuestionSet(assignedSet); 
        attempt.setStatus("STARTED");
        attemptRepo.save(attempt);

        return ResponseEntity.ok(Map.of(
            "status", "STARTED",
            "attemptId", attempt.getId(),
            "set", assignedSet,
            "levelId", lId,
            "partId", pId,
            "moduleId", mId
        ));

    } catch (Exception e) {
        return ResponseEntity.status(500).body(Map.of("message", "Database Error: " + e.getMessage()));
    }
}
 @PostMapping("/submit")
    public ResponseEntity<?> submitExam(@RequestBody Map<String, Object> payload) {
        try {
            Long attemptId = Long.parseLong(payload.get("attemptId").toString());
            List<Map<String, Object>> answers = (List<Map<String, Object>>) payload.get("answers");

            StudentExamAttempt attempt = attemptRepo.findById(attemptId)
                    .orElseThrow(() -> new RuntimeException("Attempt not found"));

            int totalObtainedMarks = 0;
            int totalPossibleMarks = 0;


            for (Map<String, Object> ans : answers) {
                Long qId = Long.parseLong(ans.get("questionId").toString());
                String studentAnsText = ans.get("answerText") != null ? ans.get("answerText").toString() : "";

                var question = questionRepository.findById(qId).orElseThrow();

                int aiScore;

                // -------------------- EXACT MATCH CHECK --------------------
                String studentAnsNormalized = studentAnsText.trim().replaceAll("\\s+", " ").replaceAll("[^a-zA-Z0-9 ]", "");
                String correctAnsNormalized = question.getCorrectAnswer() != null ?
                        question.getCorrectAnswer().trim().replaceAll("\\s+", " ").replaceAll("[^a-zA-Z0-9 ]", "")
                        : "";

                if (!studentAnsNormalized.isEmpty() && studentAnsNormalized.equalsIgnoreCase(correctAnsNormalized)) {
                    aiScore = question.getMarks(); // FULL MARKS
                } else {
                    aiScore = gradingService.getScoreFromAI(
                            question.getQuestionText(),
                            question.getCorrectAnswer(),
                            studentAnsText,
                            question.getMarks()
                    );
                }
               String aiFeedback = gradingService.getFeedbackFromAI(
        question.getQuestionText(),
        question.getCorrectAnswer(),
        studentAnsText
);

                // -------------------- SAVE ANSWER --------------------
                StudentAnswer sAns = new StudentAnswer();
                sAns.setAttemptId(attemptId);
                sAns.setQuestionId(qId);
                sAns.setSubmittedAnswer(studentAnsText);
                sAns.setMarksAwarded(aiScore);
                sAns.setQuestionTotalMarks(question.getMarks());

                if (aiScore == 0) sAns.setStatus("WRONG");
                else if (aiScore < question.getMarks()) sAns.setStatus("PARTIAL");
                else sAns.setStatus("CORRECT");

                sAns.setAiFeedback(aiFeedback);

                answerRepo.save(sAns);

                totalObtainedMarks += aiScore;
totalPossibleMarks += question.getMarks(); // 👈 admin ke marks ka total

            }

            // -------------------- UPDATE ATTEMPT --------------------
            attempt.setStatus("COMPLETED");
            attempt.setTotalMarks(totalObtainedMarks);
attempt.setTotalPossibleMarks(totalPossibleMarks);

            attempt.setCompletedAt(LocalDateTime.now());

            Optional<ExamPassword> ep = passwordRepo.findByLevelIdAndPartIdAndModuleId(
                    attempt.getLevelId(), attempt.getPartId(), attempt.getModuleId()
            );

            attempt.setResultDate(ep.isPresent() && ep.get().getResultDate() != null
                    ? ep.get().getResultDate()
                    : LocalDateTime.now().plusDays(2));

            attemptRepo.save(attempt);

            return ResponseEntity.ok(Map.of(
                    "status", "SUCCESS",
                    "attemptId", attempt.getId(),
                    "message", "Exam submitted! Result will be available on: " + attempt.getResultDate()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    /**
     * STEP 3: GET RESULT DETAILS
     * Bache ko uska detailed report dikhane ke liye (Result date ke baad).
     */
   @GetMapping("/result-details/{attemptId}")
public ResponseEntity<?> getResultDetails(@PathVariable Long attemptId) {
    try {

        StudentExamAttempt attempt = attemptRepo.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        Optional<ExamPassword> ep = passwordRepo.findByLevelIdAndPartIdAndModuleId(
                attempt.getLevelId(),
                attempt.getPartId(),
                attempt.getModuleId()
        );

        if (ep.isPresent() && ep.get().getResultDate() != null) {

            // ✅ FORCE INDIA TIMEZONE
            LocalDateTime now = LocalDateTime.now(java.time.ZoneId.of("Asia/Kolkata"));
            LocalDateTime resultUnlockDate = ep.get().getResultDate();

            // 🔍 DEBUG PRINT (remove later if you want)
            System.out.println("Current IST Time: " + now);
            System.out.println("Result Unlock Time: " + resultUnlockDate);

            // ✅ SAFE COMPARISON
            if (now.isBefore(resultUnlockDate)) {
                return ResponseEntity.status(403).body(Map.of(
                        "status", "LOCKED",
                        "message", "Result will unlock at: " + resultUnlockDate
                ));
            }
        }

        // ✅ RESULT UNLOCKED
        List<StudentAnswer> breakdown = answerRepo.findByAttemptId(attemptId);

        return ResponseEntity.ok(Map.of(
                "status", "UNLOCKED",
                "totalMarks", attempt.getTotalMarks(),
                "totalPossibleMarks", attempt.getTotalPossibleMarks(),
                "breakdown", breakdown
        ));

    } catch (Exception e) {
        return ResponseEntity.status(500).body(Map.of(
                "status", "ERROR",
                "message", e.getMessage()
        ));
    }
}
@GetMapping("/topic-progress/{attemptId}")
public ResponseEntity<?> getTopicProgress(@PathVariable Long attemptId) {
    try {

        StudentExamAttempt attempt = attemptRepo.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        // Result unlock check (same logic as result-details)
        Optional<ExamPassword> ep = passwordRepo.findByLevelIdAndPartIdAndModuleId(
                attempt.getLevelId(),
                attempt.getPartId(),
                attempt.getModuleId()
        );

        if (ep.isPresent() && ep.get().getResultDate() != null) {
            LocalDateTime now = LocalDateTime.now(java.time.ZoneId.of("Asia/Kolkata"));
            if (now.isBefore(ep.get().getResultDate())) {
                return ResponseEntity.status(403).body(Map.of(
                        "status", "LOCKED",
                        "message", "Result not unlocked yet"
                ));
            }
        }

        // 🔥 Get all answers of this attempt
        List<StudentAnswer> answers = answerRepo.findByAttemptId(attemptId);

        // 🔥 Group topic wise
        Map<String, int[]> topicMap = new java.util.HashMap<>();

        for (StudentAnswer ans : answers) {

            var question = questionRepository.findById(ans.getQuestionId()).orElse(null);
            if (question == null || question.getTopic() == null) continue;

            String topicName = question.getTopic().getName();

            topicMap.putIfAbsent(topicName, new int[]{0, 0});
            topicMap.get(topicName)[0] += ans.getMarksAwarded();        // obtained
            topicMap.get(topicName)[1] += ans.getQuestionTotalMarks();  // total
        }

        // 🔥 Convert to List format
        List<Map<String, Object>> result = new java.util.ArrayList<>();

        for (String topic : topicMap.keySet()) {
            int obtained = topicMap.get(topic)[0];
            int total = topicMap.get(topic)[1];

            result.add(Map.of(
                    "topicName", topic,
                    "obtainedMarks", obtained,
                    "totalMarks", total
            ));
        }

        return ResponseEntity.ok(result);

    } catch (Exception e) {
        return ResponseEntity.status(500).body(Map.of(
                "status", "ERROR",
                "message", e.getMessage()
        ));
    }
}
}

