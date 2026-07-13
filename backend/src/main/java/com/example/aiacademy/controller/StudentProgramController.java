// package com.example.aiacademy.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.aiacademy.model.javafiles.Level;
// import com.example.aiacademy.model.javafiles.Mcq;
// import com.example.aiacademy.model.javafiles.Part;
// import com.example.aiacademy.model.javafiles.Topic;
// import com.example.aiacademy.model.javafiles.Video;
// import com.example.aiacademy.repo.LevelRepository;
// import com.example.aiacademy.repo.McqRepository;
// import com.example.aiacademy.repo.ModuleRepository;
// import com.example.aiacademy.repo.PartRepository;
// import com.example.aiacademy.repo.TopicRepository;
// import com.example.aiacademy.repo.VideoRepository;

// @RestController
// @RequestMapping("/api/student/program")
// @CrossOrigin(origins = "*") // Frontend connectivity ke liye zaroori
// public class StudentProgramController {

//     @Autowired private LevelRepository levelRepo;
//     @Autowired private PartRepository partRepo;
//     @Autowired private ModuleRepository moduleRepo;
//     @Autowired private TopicRepository topicRepo;
//     @Autowired private VideoRepository videoRepo;
//     @Autowired private McqRepository mcqRepo;

//     // 1. All Levels
//     @GetMapping("/levels")
//     public List<Level> getLevels() { 
//         return levelRepo.findAll(); 
//     }

//     // 2. Parts by Level
//     @GetMapping("/parts/{levelId}")
//     public List<Part> getParts(@PathVariable Long levelId) { 
//         return partRepo.findByLevelId(levelId); 
//     }

//     // 3. Modules by Part
//     @GetMapping("/modules/{partId}")
//     public List<com.example.aiacademy.model.javafiles.Module> getModules(@PathVariable Long partId) {
//         return moduleRepo.findByPartId(partId);
//     }

//     // 4. Topics by Module
//     @GetMapping("/topics/{moduleId}")
//     public List<Topic> getTopics(@PathVariable Long moduleId) {
//         return topicRepo.findByModule_Id(moduleId);
//     }

//     // 5. Videos by Topic
//     @GetMapping("/videos/{topicId}")
//     public List<Video> getVideos(@PathVariable Long topicId) { 
//         return (List<Video>) videoRepo.findByTopic_Id(topicId); 
//     }
    
//     // 6. Single Video Details (Player page ke liye)
//     @GetMapping("/video-details/{videoId}")
//     public Video getVideoDetails(@PathVariable Long videoId) {
//         return videoRepo.findById(videoId).orElseThrow();
//     }

//     @GetMapping("/mcqs/{videoId}")
//     public List<Mcq> getMcqs(@PathVariable Long videoId) { 
//         return mcqRepo.findByVideoId(videoId); 
//     }
// }