package com.example.aiacademy;

import com.example.aiacademy.model.User;
import com.example.aiacademy.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {

    String adminEmail = "admin@gmail.com";

    if (!userRepo.existsByEmail(adminEmail)) {
      User admin = new User(
          "Admin",
          adminEmail,
          passwordEncoder.encode("Admin@123"),
          "ADMIN",
          0);

      admin.setProgress(100);

      userRepo.save(admin);

      System.out.println("Created default admin: admin@gmail.com / Admin@123");
    }
  }
}
