package com.example.aiacademy.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.model.javafiles.Level;

public interface LevelRepository extends JpaRepository<Level, Long> {}