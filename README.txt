AI Academy — Full Project (Admin + MySQL + JWT + Google Drive workflow)
---------------------------------------------------------------------
What I built for you:
- Frontend public pages (Home, Syllabus, Projects, Company, Contact).
- Frontend private student pages and student files viewer.
- Admin page to add Google Drive file IDs (admin.html).
- Backend Spring Boot with MySQL (DB name AIAcademy), JPA, BCrypt, JWT.
- Default admin: admin@gmail.com / Admin@123
- Contact form now posts to backend and stores messages.

Important setup steps:
1) Install MySQL and create database named AIAcademy.
   mysql> CREATE DATABASE AIAcademy;
2) Edit backend/src/main/resources/application.properties:
   - set spring.datasource.username and spring.datasource.password
   - set jwt.secret to a random secure string
3) Build & run backend:
   cd backend
   mvn package
   mvn spring-boot:run
4) Open http://localhost:8080/ (frontend served by backend).
5) Admin workflow for videos:
   - Upload MP4 to Google Drive; set sharing to "Anyone with link"
   - Copy share link or file id and paste into frontend/admin.html and Save.
   - Students will see the new file in Student -> Files page after login.

Notes:
- This is a functional demo. For production, secure secrets, use HTTPS, CDN for large files, and stronger anti-download measures.
