package com.example.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.auth.configuration.security.JwtFilterRequest;
import com.example.auth.service.SupplierAuthentication;
import com.example.auth.service.UserAuthentication;

@Configuration
public class SecurityConfiguration {

  @Autowired
  UserAuthentication userAuthentication;
  @Autowired
  SupplierAuthentication supplierAuthentication;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      return http.cors()
      .and()
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/users/subs", "/users/auth", "/supplier/auth","/supplier/create").permitAll()
      .anyRequest().authenticated()
      .and()
      .addFilter(new JwtFilterRequest(getAuthenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().build();
  }

  @Bean
  AuthenticationManager authenticationManager() {

    DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider();
    userProvider.setUserDetailsService(userAuthentication);
    userProvider.setPasswordEncoder(passwordEncoder());

    DaoAuthenticationProvider supplierProvider = new DaoAuthenticationProvider();
    supplierProvider.setUserDetailsService(supplierAuthentication);
    supplierProvider.setPasswordEncoder(passwordEncoder());

    return new ProviderManager(userProvider, supplierProvider);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.applyPermitDefaultValues();
    corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

  private AuthenticationManager getAuthenticationManager() {
    return authenticationManager();
  }
}
