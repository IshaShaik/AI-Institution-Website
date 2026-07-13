package com.example.aiacademy;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aiacademy.model.User;
import com.example.aiacademy.repo.UserRepository;
import com.example.aiacademy.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private JwtUtil jwtUtil;

  // ---------- REGISTER ----------
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String pass = body.get("password");
    String name = body.getOrDefault("name", "");
    String role = body.getOrDefault("role", "STUDENT");
String collegeName = body.get("collegeName");
String studentClass = body.get("studentClass");
String section = body.get("section");

    if (email == null || pass == null || email.isEmpty() || pass.isEmpty()) {
      return ResponseEntity.badRequest().body(Map.of("message", "email & password required"));
    }

    if (userRepo.existsByEmail(email)) {
      return ResponseEntity.badRequest().body(Map.of("message", "Email already exists"));
    }

    // SAVE PLAIN TEXT PASSWORD (No encoding)
    User u = new User(name, email, pass, role, 0);
    u.setCollegeName(collegeName);
u.setStudentClass(studentClass);
u.setSection(section);
    userRepo.save(u);

    return ResponseEntity.ok(Map.of("message", "registered"));
  }

  // ---------- LOGIN ----------
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String pass = body.get("password");

    if (email == null || pass == null || email.isEmpty() || pass.isEmpty()) {
      return ResponseEntity.status(400).body(Map.of("message", "Missing email or password"));
    }

    var op = userRepo.findByEmail(email);
    if (op.isEmpty()) {
      return ResponseEntity.status(401).body(Map.of("message", "invalid")); // User not found
    }

    User u = op.get();

    // -------- PLAIN TEXT CHECK --------
    if (!pass.equals(u.getPassword())) {
      return ResponseEntity.status(401).body(Map.of("message", "invalid")); // Wrong password
    }

    String token = jwtUtil.generateToken(u.getEmail(), u.getRole());

    return ResponseEntity.ok(Map.of(
    "token", token,
    "role", u.getRole(),
    "name", u.getName(),
    "userId", u.getId(),
    "profileImage", (u.getProfileImage() != null ? u.getProfileImage() : "student.png")
));
  }
}
