package com.example.aiacademy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aiacademy.model.javafiles.card;

@Repository
public interface Cardrepo extends JpaRepository<card, Long> {
}