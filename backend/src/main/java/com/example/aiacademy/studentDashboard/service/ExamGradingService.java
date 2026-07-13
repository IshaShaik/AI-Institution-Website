package com.example.aiacademy.studentDashboard.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExamGradingService {

 
    // ✅ Groq API Endpoint
  @Value("${groq.api.key}")
private String apiKey;

private static final String GROQ_URL =
        "https://api.groq.com/openai/v1/chat/completions";

    public int getScoreFromAI(String question,
                             String correctAnswer,
                             String studentAnswer,
                             int maxMarks) {

        // 1️⃣ Empty answer check
        if (studentAnswer == null || studentAnswer.trim().isEmpty()) {
            return 0;
        }

        RestTemplate restTemplate = new RestTemplate();

        // 🔥 Aapka original Teacher-like Prompt
        String prompt = String.format("""
            You are a strict but fair human exam evaluator.
            
            QUESTION:
            %s
            
            TEACHER ANSWER:
            %s
            
            STUDENT ANSWER:
            %s
            
            MAX MARKS:
            %d
            
            GRADING RULES:
            1. If answer is empty, meaningless, or unrelated -> 0 marks.
            2. If concept and logic are fully correct -> FULL marks.
            3. If core logic or algorithm is correct BUT there is a small mistake -> PARTIAL marks.
            4. Do NOT judge only final output. Judge student intent and logic like a real teacher.
            5. If logic is completely wrong -> 0 marks.
            
            RESPONSE FORMAT:
            Return ONLY ONE INTEGER between 0 and %d.
            No explanation. No text.
            
            FINAL SCORE:
            """, question, correctAnswer, studentAnswer, maxMarks, maxMarks);

        try {
            // 2️⃣ Groq compatible Request Body (Llama 3.3 model)
            Map<String, Object> requestBody = Map.of(
                    "model", "llama-3.3-70b-versatile",
                    "messages", List.of(
                            Map.of("role", "system", "content", "You are a human exam evaluator."),
                            Map.of("role", "user", "content", prompt)
                    ),
                    "temperature", 0
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // 3️⃣ API Call to Groq
            ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_URL, entity, Map.class);

            // 4️⃣ Response Extraction
            List choices = (List) response.getBody().get("choices");
            Map firstChoice = (Map) choices.get(0);
            Map message = (Map) firstChoice.get("message");

            String aiText = message.get("content").toString().trim();
            int aiScore = safeParseScore(aiText, maxMarks);

            // 🔐 Aapka original Safety Check for coding
            if (aiScore == 0 &&
                studentAnswer.contains("int") &&
                studentAnswer.contains("+")) {
                aiScore = Math.max(1, maxMarks / 2);
            }

            if (aiScore < 0) return 0;
            if (aiScore > maxMarks) return maxMarks;

            return aiScore;

        } catch (Exception e) {
            System.err.println("Groq Grading Error: " + e.getMessage());
            return 0;
        }
    }
    public String getFeedbackFromAI(String question,
                                String correctAnswer,
                                String studentAnswer) {

    if (studentAnswer == null || studentAnswer.trim().isEmpty()) {
        return "Answer not provided.";
    }

    RestTemplate restTemplate = new RestTemplate();

    String prompt = String.format("""
        You are a helpful exam evaluator.

        QUESTION:
        %s

        TEACHER ANSWER:
        %s

        STUDENT ANSWER:
        %s

        Give only one short feedback sentence.
        Do not give marks.
        """, question, correctAnswer, studentAnswer);

    try {

        Map<String, Object> requestBody = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "system", "content", "You are an exam evaluator."),
                        Map.of("role", "user", "content", prompt)
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(GROQ_URL, entity, Map.class);

        List choices = (List) response.getBody().get("choices");
        Map firstChoice = (Map) choices.get(0);
        Map message = (Map) firstChoice.get("message");

        return message.get("content").toString().trim();

    } catch (Exception e) {
        return "Feedback unavailable.";
    }
}


    // 🔢 Number extraction logic (Original)
    private int safeParseScore(String aiText, int maxMarks) {
        if (aiText == null || aiText.isBlank()) return 0;
        if (aiText.matches("\\d+")) {
            return Math.min(Integer.parseInt(aiText), maxMarks);
        }
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(aiText);
        if (matcher.find()) {
            return Math.min(Integer.parseInt(matcher.group()), maxMarks);
        }
        return 0;
    }
}
