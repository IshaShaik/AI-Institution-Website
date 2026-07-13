package com.example.aiacademy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password; 

    private String role; 

    private int progress = 0;
     private String collegeName;
   // optional (school / college dono ke liye)
private String studentClass; // 11 / 12
private String section;  

    // --- Naya field add kiya ---
  private String profileImage = "student.png"; // "images/" hata dein

    public User() {
    }

    public User(String name, String email, String password, String role, int progress) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.progress = progress;
           // A / B / C

    }

    // --- Naye Getters aur Setters ---
    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    // --- Aapke purane Getters aur Setters ---
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}











// package com.example.aiacademy.model;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "users")
// public class User {

//   @Id
//   @GeneratedValue(strategy = GenerationType.IDENTITY)
//   private Long id;

//   private String name;

//   @Column(unique = true)
//   private String email;

//   private String password; // Plain-text password

//   private String role; // STUDENT / ADMIN

//   private int progress = 0;

//   public User() {
//   }

//   public User(String name, String email, String password, String role, int progress) {
//     this.name = name;
//     this.email = email;
//     this.password = password;
//     this.role = role;
//     this.progress = progress;
//   }

//   // Getters and setters
//   public Long getId() {
//     return id;
//   }

//   public String getName() {
//     return name;
//   }

//   public void setName(String name) {
//     this.name = name;
//   }

//   public String getEmail() {
//     return email;
//   }

//   public void setEmail(String email) {
//     this.email = email;
//   }

//   public String getPassword() {
//     return password;
//   }

//   public void setPassword(String password) {
//     this.password = password;
//   }

//   public String getRole() {
//     return role;
//   }

//   public void setRole(String role) {
//     this.role = role;
//   }

//   public int getProgress() {
//     return progress;
//   }

//   public void setProgress(int progress) {
//     this.progress = progress;
//   }
// }
