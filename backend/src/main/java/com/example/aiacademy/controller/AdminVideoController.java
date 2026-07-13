// package com.example.aiacademy.controller;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.aiacademy.model.javafiles.Video;
// import com.example.aiacademy.repo.VideoRepository;
// import com.example.aiacademy.service.VideoDeleteService;

// @RestController
// @RequestMapping("/api/admin")
// @CrossOrigin("*")
// public class AdminVideoController {

//     @Autowired
//     private VideoRepository videoRepo;

//     @Autowired
//     private VideoDeleteService  deleteService;

//     @DeleteMapping("/video/{id}")
//     public String deleteVideo(@PathVariable Long id) {

//         Optional<Video> videoOpt = videoRepo.findById(id);
//         if (videoOpt.isEmpty()) {
//             return "Video not found";
//         }

//         Video video = videoOpt.get();

//         // 1️⃣ Upload folder se delete
//         deleteService.deleteFileIfExists(video.getVideoPath());
//         deleteService.deleteFileIfExists(video.getThumbnailPath());

//         // 2️⃣ Database se delete
//         videoRepo.deleteById(id);

//         return "Video deleted successfully";
//     }
// }
