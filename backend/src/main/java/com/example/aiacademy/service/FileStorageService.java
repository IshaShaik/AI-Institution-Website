package com.example.aiacademy.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final String VIDEO_DIR = "uploads/videos/";
    private final String THUMB_DIR = "uploads/thumbs/";

    public String saveVideo(MultipartFile file) throws IOException {
    // Directories create karein agar nahi hain
    File dir = new File(VIDEO_DIR);
    if (!dir.exists()) dir.mkdirs();

    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    // Absolute Path use karein transfer ke liye
    File dest = new File(dir.getAbsolutePath() + File.separator + fileName);
    file.transferTo(dest);
    
    return VIDEO_DIR + fileName;
}

public String saveThumb(MultipartFile file) throws IOException {
    File dir = new File(THUMB_DIR);
    if (!dir.exists()) dir.mkdirs();

    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    File dest = new File(dir.getAbsolutePath() + File.separator + fileName);
    file.transferTo(dest);
    
    return THUMB_DIR + fileName;
}
}
