package com.example.auth.configuration.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtFilterRequest extends BasicAuthenticationFilter {

  public JwtFilterRequest(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilterRequest.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String authorizationHeader = request.getHeader("Authorization");
    
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      LOGGER.error("Invalid token");
      filterChain.doFilter(request, response);
      return;
    }

    // je récupère le token
    String token = authorizationHeader.substring(7);

    // Je récupère la clé stockée
    String secretKey = JWTSingleton.getInstance().getSecretKey();

    // je transforme la clé en HMAC-SHA 256
    SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    
    // je parse le token JWT pour récupérer les données.
    Jws<Claims> jws = Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token);

    String username = jws.getBody().getSubject();
    Date exp = jws.getBody().getExpiration();

    // Date d'expiration incorrecte, je renvoies un message
    if (exp.before(new Date())) {
      LOGGER.error("Invalid token expiration date");
      filterChain.doFilter(request, response);
      return;
    }

    LOGGER.debug("Valid token");

    List<GrantedAuthority> authorities = new ArrayList<>();
    
    UsernamePasswordAuthenticationToken uToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(uToken);
    filterChain.doFilter(request, response);
  }
}
