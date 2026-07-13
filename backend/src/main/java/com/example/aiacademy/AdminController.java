package com.example.aiacademy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.aiacademy.model.FileRecord;
import com.example.aiacademy.model.User;
import com.example.aiacademy.repo.FileRecordRepository;
import com.example.aiacademy.repo.UserRepository;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
  @Autowired private FileRecordRepository fileRepo;
  @Autowired private UserRepository userRepo;
  @PostMapping("/upload-metadata")
  public ResponseEntity<?> uploadMeta(@RequestBody Map<String,String> body){
    String title = body.get("title"), type = body.get("type"), driveId = body.get("driveFileId"), desc = body.get("description");
    if(title==null || type==null || driveId==null) return ResponseEntity.badRequest().body(Map.of("message","missing"));
    FileRecord f = new FileRecord(); f.setTitle(title); f.setType(type); f.setDriveFileId(driveId); f.setDescription(desc);
    fileRepo.save(f);
    return ResponseEntity.ok(Map.of("message","saved"));
  }
  @PostMapping("/update-profile-pic")
    public ResponseEntity<?> updateProfilePic(@RequestParam("file") MultipartFile file,
                                              @RequestParam("userId") Long userId) {
        try {
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Save file to static/images
            String originalFilename = file.getOriginalFilename();
            String filename = user.getId() + "_" + System.currentTimeMillis() + "_" + originalFilename;

            Path path = Paths.get("src/main/resources/static/images/" + filename);
            Files.write(path, file.getBytes());

            // Update user DB
            user.setProfileImage(filename);
            userRepo.save(user);

            return ResponseEntity.ok().body(
                    java.util.Collections.singletonMap("fileName", filename)
            );
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    java.util.Collections.singletonMap("message", "Failed to save file")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    java.util.Collections.singletonMap("message", e.getMessage())
            );
        }
    }

}
