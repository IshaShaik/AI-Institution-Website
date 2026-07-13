package com.example.aiacademy;

import com.example.aiacademy.model.ContactMessage;
import com.example.aiacademy.repo.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
  @Autowired
  private ContactMessageRepository repo;

  @GetMapping("/all")
  public ResponseEntity<?> getAllMessages() {
    return ResponseEntity.ok(repo.findAll());
  }

  @PostMapping("/send")
  public ResponseEntity<?> send(@RequestBody ContactMessage msg) {
    repo.save(msg);
    return ResponseEntity.ok(java.util.Map.of("message", "received"));
  }
}
