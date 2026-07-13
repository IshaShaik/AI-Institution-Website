package com.example.aiacademy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aiacademy.model.javafiles.ModuleEntity;

public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {
    List<ModuleEntity> findByPartId(Long partId);
}