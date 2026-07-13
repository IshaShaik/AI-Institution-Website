package com.example.aiacademy.repo;
import com.example.aiacademy.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {}
