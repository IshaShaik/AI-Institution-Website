package com.example.aiacademy.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expirationMs}")
  private long expirationMs;

  public String generateToken(String subject, String role) {
    return Jwts.builder().setSubject(subject).claim("role", role).setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
        .signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    return claimsResolver.apply(claims);
  }

  public boolean validateToken(String token, String username) {
    final String u = extractUsername(token);
    return (u.equals(username) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }

  public String extractEmail(String token) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'extractEmail'");
  }
}
