package com.example.aiacademy.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.model.javafiles.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {}