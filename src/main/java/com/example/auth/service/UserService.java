package com.example.auth.service;

import com.example.auth.model.AuthenticationRequest;
import com.example.auth.model.AuthenticationResponse;
import com.example.auth.model.UserModel;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserModel foundedUser = userRepository.findByUsername(username);
    if (foundedUser == null)
    return null;
    String name = foundedUser.getUsername();
    String pwd = foundedUser.getPassword();
    return new User(name, pwd, new ArrayList<>());
  }

  public ResponseEntity<?> subscribe(AuthenticationRequest authenticationRequest) {
    String username = authenticationRequest.getUsername();
    String password = authenticationRequest.getPassword();
    String fullName = authenticationRequest.getFullName();
    String email = authenticationRequest.getEmail();
    UserModel userModel = new UserModel();
    userModel.setUsername(username);
    userModel.setPassword(password);
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

  public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest){
    String username = authenticationRequest.getUsername();
    String password = authenticationRequest.getPassword();
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (Exception e) {
      return ResponseEntity.ok(new AuthenticationResponse("error during client authentication for: " + username));
    }
    return ResponseEntity.ok(new AuthenticationResponse("successful authentication for client: " + username));
  }

  public UserModel update(UserModel userModel){
    UserModel user1 = userRepository.findByUsername(userModel.getUsername());
    System.out.println(userModel);
    if(user1 == null) {
      System.out.println("user not found");
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
}
