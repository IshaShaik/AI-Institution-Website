package com.example.aiacademy.service;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class VideoDeleteService {

    public void deleteFileIfExists(String path) {
        if (path == null) return;

        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
