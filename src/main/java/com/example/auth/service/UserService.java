package com.example.auth.service;

import com.example.auth.configuration.security.JWTSingleton;
import com.example.auth.model.authentication.AuthenticationRequest;
import com.example.auth.model.authentication.AuthenticationResponse;
import com.example.auth.model.users.UserModel;
import com.example.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final UserAuthentication userAuthentication;

  private final AuthenticationManager authenticationManager;

  public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, UserAuthentication userAuthentication) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.userAuthentication = userAuthentication;
  }

  public ResponseEntity<AuthenticationResponse> subscribe(AuthenticationRequest authenticationRequest) {
    String username = authenticationRequest.getUsername();
    String password = authenticationRequest.getPassword();
    String fullName = authenticationRequest.getFullName();
    String email = authenticationRequest.getEmail();

    UserModel userModel = new UserModel();
    userModel.setUsername(username);
    userModel.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    userModel.setEmail(email);
    userModel.setFullName(fullName);
    userModel.setRole("customer");

    try {
      userRepository.save(userModel);
    } catch (Exception e) {
      return ResponseEntity.ok(new AuthenticationResponse("error during client subscription: " + username));
    }

    return ResponseEntity.ok(new AuthenticationResponse("successful subscription for: " + username));
  }

  public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest){
    String username = authenticationRequest.getUsername();
    String password = authenticationRequest.getPassword();

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (Exception e) {

      LOGGER.error(e.getMessage());

      return ResponseEntity.ok(new AuthenticationResponse("error during client authentication for: " + username));
    }

    UserDetails loadedUser = userAuthentication.loadUserByUsername(username);

    String generatedToken = JWTSingleton.getInstance(loadedUser.getUsername(), new HashMap<>()).getToken();

    return ResponseEntity.ok()
      .header("Authorization", generatedToken)
      .header("Access-Control-Expose-Headers", "Authorization")
      .body(new AuthenticationResponse());
  }

  public UserModel update(UserModel userModel){
    UserModel user1 = userRepository.findByUsername(userModel.getUsername());

    LOGGER.info("{}", userModel);

    if(user1 == null) {
      LOGGER.error("User not found");
      return userRepository.save(userModel);
    } else {
      user1.setFullName(userModel.getFullName());
      user1.setUsername(userModel.getUsername());
      user1.setEmail(userModel.getEmail());
      user1.setPassword(userModel.getPassword());
      userRepository.save(user1);
    }
    return userModel;
  }

  public UserModel getByUsername(String username) { return userRepository.findByUsername(username);}

}
