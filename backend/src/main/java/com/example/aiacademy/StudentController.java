package com.example.aiacademy;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Fix: Controller use karein
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.aiacademy.model.User;
import com.example.aiacademy.repo.FileRecordRepository;
import com.example.aiacademy.repo.UserRepository;
import com.example.aiacademy.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller // Fix: HTML render karne ke liye @Controller hona chahiye
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired private UserRepository userRepo;
    @Autowired private FileRecordRepository fileRepo;
    @Autowired private JwtUtil jwtUtil;

    // Fix: Ye method HTML page dikhayega
    @GetMapping("/programhub")
    public String showProgramHub(Model model, Principal principal) {
        if (principal == null) return "redirect:/login"; 
        String email = principal.getName();
        User user = userRepo.findByEmail(email).orElseThrow();
        model.addAttribute("user", user); // 'user' object model mein add kiya
        return "programhub"; 
    }

    // Fix: @ResponseBody lagaya taaki ye JSON data de
    @GetMapping("/files")
    @ResponseBody
    public ResponseEntity<?> files() {
        return ResponseEntity.ok(fileRepo.findAll());
    }

    @GetMapping("/progress")
    @ResponseBody
    public ResponseEntity<?> getProgress(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        String token = auth.substring(7);
        String email = jwtUtil.extractEmail(token);
        User user = userRepo.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(Map.of("progress", user.getProgress()));
    }
}









// package com.example.aiacademy;

// import java.security.Principal;
// import java.util.List;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.aiacademy.model.FileRecord;
// import com.example.aiacademy.model.User;
// import com.example.aiacademy.repo.FileRecordRepository;
// import com.example.aiacademy.repo.UserRepository;
// import com.example.aiacademy.security.JwtUtil;

// import jakarta.servlet.http.HttpServletRequest;

// @RestController
// @RequestMapping("/api/student")
// @CrossOrigin(origins = "*")
// public class StudentController {

//   @Autowired
//   private UserRepository userRepo;

//   @Autowired
//   private FileRecordRepository fileRepo;

//   @Autowired
//   private JwtUtil jwtUtil;

//   // --------------- FILE LIST ----------------
//   @GetMapping("/files")
//   public ResponseEntity<?> files() {
//     List<FileRecord> all = fileRepo.findAll();
//     return ResponseEntity.ok(all);
//   }

//   // --------------- GET PROGRESS ----------------
//   @GetMapping("/progress")
//   public ResponseEntity<?> getProgress(HttpServletRequest request) {

//     String auth = request.getHeader("Authorization");
//     if (auth == null || !auth.startsWith("Bearer "))
//       return ResponseEntity.status(401).body(Map.of("message", "Missing token"));

//     String token = auth.substring(7);
//     String email = jwtUtil.extractEmail(token);

//     User user = userRepo.findByEmail(email).orElseThrow();

//     return ResponseEntity.ok(Map.of("progress", user.getProgress()));
//   }
//   @GetMapping("/programhub")
// public String showProgramHub(Model model, Principal principal) { // <--- Yahan 'Model model' hona zaroori hai
//     String email = principal.getName();
//     User user = userRepo.findByEmail(email).orElseThrow();

//     model.addAttribute("user", user); // Ab ye error nahi dega
//     return "programhub";
// }
//   // --------------- UPDATE PROGRESS ----------------
//   @PostMapping("/progress/update")
//   public ResponseEntity<?> updateProgress(@RequestBody Map<String, Integer> body,
//       HttpServletRequest request) {

//     String auth = request.getHeader("Authorization");
//     if (auth == null || !auth.startsWith("Bearer "))
//       return ResponseEntity.status(401).body(Map.of("message", "Missing token"));

//     String token = auth.substring(7);
//     String email = jwtUtil.extractEmail(token);

//     User user = userRepo.findByEmail(email).orElseThrow();

//     int p = body.getOrDefault("progress", user.getProgress());
//     if (p < 0)
//       p = 0;
//     if (p > 100)
//       p = 100;

//     user.setProgress(p);
//     userRepo.save(user);

//     return ResponseEntity.ok(Map.of("status", "updated", "progress", p));
//   }

// }
