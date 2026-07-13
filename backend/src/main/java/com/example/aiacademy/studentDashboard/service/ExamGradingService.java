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

    // ✅ Groq API Key yahan dalein
    private static final String GROQ_API_KEY = "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";

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

// import java.util.List;
// import java.util.Map;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// @Service
// public class ExamGradingService {

//     // ⚠️ Best practice: isey baad me application.properties me shift karna
//     private final String API_KEY = "AIzaSyAkqKHWoTuL5pSCpKNLxp9a7y8J2EWhVwg";
//     private final String API_URL =
//         "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
//             + API_KEY;

//     public int getScoreFromAI(String question,
//                               String correctAnswer,
//                               String studentAnswer,
//                               int maxMarks) {

//         // 1️⃣ Agar student ne kuch bhi nahi likha
//         if (studentAnswer == null || studentAnswer.trim().isEmpty()) {
//             return 0;
//         }

//         RestTemplate restTemplate = new RestTemplate();

//         // 2️⃣ CLEAR PROMPT (DB ke marks ke hisaab se grading)
//      String promptText = String.format(
//     """
//     You are a professional and expert Computer Science Professor. 
//     Your task is to grade a student's answer based on conceptual understanding and logic.

//     [EXAM DATA]
//     - Question: %s
//     - Reference/Model Answer: %s
//     - Student's Answer: %s
//     - Maximum Possible Marks: %d

//     [GRADING PHILOSOPHY]
//     1. PRIORITIZE MEANING: If the student's answer has the same CORE MEANING as the reference answer, give FULL MARKS. Do not care about synonyms or sentence structure.
//     2. CODING LOGIC: For programming questions, if the logic is correct and solve the problem (like the sum of 2 numbers), give FULL MARKS even if variable names (like 'num1' vs 'a') or comments are different.
//     3. UNDERSTANDING: If the student explains the concept accurately in their own simple English, award full credit. 
//     4. PARTIAL CREDIT: Only deduct marks if a key technical part is missing. 
//     5. BE LENIENT: If the student is correct but uses different words, DO NOT deduct marks.

//     [RESPONSE RULE]
//     - Return ONLY the final integer score. 
//     - No words, no "Score: ", no explanation. 
//     - Max score allowed: %d.

//     SCORE:
//     """, 
//     question, correctAnswer, studentAnswer, maxMarks, maxMarks
// );     
//         try {
//             // 3️⃣ Gemini request body
//             Map<String, Object> requestBody = Map.of(
//                 "contents", List.of(
//                     Map.of("parts", List.of(
//                         Map.of("text", promptText)
//                     ))
//                 )
//             );

//             HttpHeaders headers = new HttpHeaders();
//             headers.setContentType(MediaType.APPLICATION_JSON);

//             HttpEntity<Map<String, Object>> entity =
//                 new HttpEntity<>(requestBody, headers);

//             ResponseEntity<Map> response =
//                 restTemplate.postForEntity(API_URL, entity, Map.class);

//             // 4️⃣ Gemini response read karna
//             List candidates = (List) response.getBody().get("candidates");
//             Map firstCandidate = (Map) candidates.get(0);
//             Map content = (Map) firstCandidate.get("content");
//             List parts = (List) content.get("parts");
//             Map firstPart = (Map) parts.get(0);

//             String aiResult = firstPart.get("text").toString().trim();

//             // 5️⃣ Safe parsing (0 se maxMarks ke beech hi)
//             return safeParseScore(aiResult, maxMarks);

//         } catch (Exception e) {
//             System.err.println("AI Grading Error: " + e.getMessage());

//             // 6️⃣ SMART FALLBACK
//             // Agar AI fail ho jaye:
//             // - kuch likha hai → partial marks
//             // - kuch nahi / nonsense → 0
//             if (studentAnswer.length() > 10) {
//                 return Math.max(1, maxMarks / 2);
//             }
//             return 0;
//         }
//     }

//     // 🔹 AI ke response ko safely integer me convert karta hai
//     private int safeParseScore(String aiText, int maxMarks) {

//         if (aiText == null || aiText.isBlank()) {
//             return 0;
//         }

//         aiText = aiText.trim();

//         // Case 1: sirf number (e.g. "2")
//         if (aiText.matches("\\d+")) {
//             int score = Integer.parseInt(aiText);
//             return Math.min(score, maxMarks);
//         }

//         // Case 2: sentence me number (e.g. "Score: 2 out of 2")
//         Pattern pattern = Pattern.compile("\\d+");
//         Matcher matcher = pattern.matcher(aiText);

//         if (matcher.find()) {
//             int score = Integer.parseInt(matcher.group());
//             return Math.min(score, maxMarks);
//         }

//         return 0;
//     }
// }
