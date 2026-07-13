// package com.example.aiacademy.controller;

// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.nio.file.StandardCopyOption;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.example.aiacademy.model.javafiles.Level;
// import com.example.aiacademy.model.javafiles.Module;
// import com.example.aiacademy.model.javafiles.Part;
// import com.example.aiacademy.model.javafiles.Topic;
// import com.example.aiacademy.model.javafiles.Video;
// import com.example.aiacademy.repo.LevelRepository;
// import com.example.aiacademy.repo.ModuleRepository;
// import com.example.aiacademy.repo.PartRepository;
// import com.example.aiacademy.repo.TopicRepository;
// import com.example.aiacademy.repo.VideoRepository;

// @RestController
// @RequestMapping("/api/admin")
// @CrossOrigin(origins = "*")
// public class AdminProgramController {

//     @Autowired private LevelRepository levelRepo;
//     @Autowired private PartRepository partRepo;
//     @Autowired private ModuleRepository moduleRepo;
//     @Autowired private TopicRepository topicRepo;
//     @Autowired private VideoRepository videoRepo;

//     /* ================= GET ================= */

//     @GetMapping("/levels")
//     public List<Level> getLevels() {
//         return levelRepo.findAll();
//     }

//     @GetMapping("/parts/{levelId}")
//     public List<Part> getParts(@PathVariable Long levelId) {
//         return partRepo.findByLevelId(levelId);
//     }

//     @GetMapping("/modules/{partId}")
//     public List<Module> getModules(@PathVariable Long partId) {
//         return moduleRepo.findByPartId(partId);
//     }

//     @GetMapping("/topics/{moduleId}")
//     public List<Topic> getTopics(@PathVariable Long moduleId) {
//         return topicRepo.findByModule_Id(moduleId);
//     }

//     /* ================= CREATE (JSON) ================= */

//     @PostMapping(
//         value = "/level",
//         consumes = MediaType.APPLICATION_JSON_VALUE,
//         produces = MediaType.APPLICATION_JSON_VALUE
//     )
//     public ResponseEntity<Level> createLevel(@RequestBody Level level) {
//         level.setId(null);
//         return ResponseEntity.ok(levelRepo.save(level));
//     }

//     @PostMapping(
//         value = "/part/{levelId}",
//         consumes = MediaType.APPLICATION_JSON_VALUE,
//         produces = MediaType.APPLICATION_JSON_VALUE
//     )
//     public ResponseEntity<Part> createPart(
//             @PathVariable Long levelId,
//             @RequestBody Part part) {

//         return levelRepo.findById(levelId).map(level -> {
//             part.setLevel(level);
//             return ResponseEntity.ok(partRepo.save(part));
//         }).orElse(ResponseEntity.notFound().build());
//     }

//     @PostMapping(
//         value = "/module/{partId}",
//         consumes = MediaType.APPLICATION_JSON_VALUE,
//         produces = MediaType.APPLICATION_JSON_VALUE
//     )
//     public ResponseEntity<Module> createModule(
//             @PathVariable Long partId,
//             @RequestBody Module module) {

//         return partRepo.findById(partId).map(part -> {
//             module.setPart(part);
//             return ResponseEntity.ok(moduleRepo.save(module));
//         }).orElse(ResponseEntity.notFound().build());
//     }

//     @PostMapping(
//         value = "/topic/{moduleId}",
//         consumes = MediaType.APPLICATION_JSON_VALUE,
//         produces = MediaType.APPLICATION_JSON_VALUE
//     )
//     public ResponseEntity<Topic> createTopic(
//             @PathVariable Long moduleId,
//             @RequestBody Topic topic) {

//         return moduleRepo.findById(moduleId).map(module -> {
//             topic.setModule(module);
//             return ResponseEntity.ok(topicRepo.save(topic));
//         }).orElse(ResponseEntity.notFound().build());
//     }

//     /* ================= VIDEO (MULTIPART) ================= */

//     @PostMapping(value = "/video/{topicId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//     public ResponseEntity<?> uploadVideo(
//             @PathVariable Long topicId,
//             @RequestParam("title") String title,
//             @RequestParam("description") String description,
//             @RequestParam("videoFile") MultipartFile videoFile,
//             @RequestParam("thumbnail") MultipartFile thumbnail) {

//         try {
//             Topic topic = topicRepo.findById(topicId)
//                     .orElseThrow(() -> new RuntimeException("Topic not found"));

//             Path videoDir = Paths.get("uploads/video");
//             Path thumbDir = Paths.get("uploads/thumb");

//             Files.createDirectories(videoDir);
//             Files.createDirectories(thumbDir);

//             String videoName = System.currentTimeMillis() + "_" + videoFile.getOriginalFilename();
//             String thumbName = System.currentTimeMillis() + "_" + thumbnail.getOriginalFilename();

//             Files.copy(videoFile.getInputStream(),
//                     videoDir.resolve(videoName),
//                     StandardCopyOption.REPLACE_EXISTING);

//             Files.copy(thumbnail.getInputStream(),
//                     thumbDir.resolve(thumbName),
//                     StandardCopyOption.REPLACE_EXISTING);

//             Video v = new Video();
//             v.setTitle(title);
//             v.setDescription(description);
//             v.setVideoPath("/uploads/video/" + videoName);
//             v.setThumbnailPath("/uploads/thumb/" + thumbName);
//             v.setTopic(topic);

//             videoRepo.save(v);

//             return ResponseEntity.ok("Video uploaded successfully");

//         } catch (Exception e) {
//             return ResponseEntity.internalServerError().body(e.getMessage());
//         }
//     }
// }
