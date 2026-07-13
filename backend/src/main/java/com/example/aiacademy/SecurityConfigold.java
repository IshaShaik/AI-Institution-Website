// package com.example.aiacademy;

// import com.example.aiacademy.security.JwtFilter;
// import com.example.aiacademy.repo.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// public class SecurityConfigold {
//   @Autowired
//   private JwtFilter jwtFilter;
//   @Autowired
//   private UserRepository userRepository;

//   @Bean
//   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//     http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
//         .requestMatchers("/api/admin/**").hasRole("ADMIN")
//         .requestMatchers("/api/student/register").permitAll()
//         .requestMatchers("/api/student/**").hasRole("STUDENT")
//         .requestMatchers("/api/auth/**").permitAll()
//         .requestMatchers("/**").permitAll()).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//     return http.build();
//   }

//   @Bean
//   PasswordEncoder passwordEncoder() {
//     return new BCryptPasswordEncoder();
//   }
// }
