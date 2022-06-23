package com.example.auth.configuration.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JWTSingleton {

  private static JWTSingleton instance;

  private String token;
  private String secretKey;

  private JWTSingleton() {

  }

  private JWTSingleton(String subject, Map<String, String> claims) {

    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    secretKey = Base64.getEncoder().encodeToString(key.getEncoded());

    token = Jwts
      .builder()
      .setSubject(subject)
      .setClaims(claims)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 3600000))
      .signWith(key)
      .compact();
  }

  public static JWTSingleton getInstance() {
    if (instance == null) {
      instance = new JWTSingleton();
    }
    return instance;
  }

  public static JWTSingleton getInstance(String subject, Map<String, String> claims) {
    if (instance == null) {
      instance = new JWTSingleton(subject, claims);
    }
    return instance;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public String getToken() {
    return token;
  }
}
