package com.example.auth.service;

import com.example.auth.model.UserModel;
import com.example.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserAuthentication implements UserDetailsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthentication.class);

  private final UserRepository userRepository;

  public UserAuthentication(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserModel foundedUser = userRepository.findByUsername(username);

    if (foundedUser == null)
      return null;

    LOGGER.info("Success");

    String name = foundedUser.getUsername();
    String pwd = foundedUser.getPassword();

    return new User(name, pwd, new ArrayList<>());
  }
}
