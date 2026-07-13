package com.example.aiacademy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.aiacademy.model.javafiles.Mcq;

public interface McqRepository extends JpaRepository<Mcq, Long> {}
