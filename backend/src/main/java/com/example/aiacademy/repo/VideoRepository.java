
package com.example.aiacademy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.model.javafiles.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

        List<Video> findByTopicId(Long topicId);
}
