package com.example.aiacademy.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.aiacademy.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // --- Profile image update karne ke liye ---
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.profileImage = ?2 WHERE u.id = ?1")
    void updateProfileImage(Long userId, String imagePath);
}




// package com.example.aiacademy.repo;

// import com.example.aiacademy.model.User;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.Optional;

// public interface UserRepository extends JpaRepository<User, Long> {
//   Optional<User> findByEmail(String email);

//   boolean existsByEmail(String email);
// }
