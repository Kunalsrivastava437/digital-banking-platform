package com.bank.transactionservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
  private final Key key;

  public JwtUtil(@Value("${app.jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  public boolean isValid(String token) {
    try {
      var claims = Jwts.parserBuilder().setSigningKey(key).build()
          .parseClaimsJws(token).getBody();
      return claims.getExpiration().after(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
