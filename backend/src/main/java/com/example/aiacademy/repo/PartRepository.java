package com.example.aiacademy.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.model.javafiles.Part;

public interface PartRepository extends JpaRepository<Part, Long> {
    
}
