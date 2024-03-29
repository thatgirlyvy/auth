package com.example.auth.controller;

import com.example.auth.model.authentication.AuthenticationRequest;
import com.example.auth.model.authentication.AuthenticationResponse;
import com.example.auth.model.users.UserModel;
import com.example.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService){ this.userService = userService;}

  @GetMapping("/dashboard")
  public String testingToken() {
    return "welcome to dashboard";
  }

  @PostMapping("/subs")
  public ResponseEntity<AuthenticationResponse> subscribeClient(@RequestBody AuthenticationRequest authenticationRequest){
    return userService.subscribe(authenticationRequest);
  }

  @PostMapping("/auth")
  public ResponseEntity<AuthenticationResponse> authenticateClient(@RequestBody AuthenticationRequest authenticationRequest){
    return userService.authenticate(authenticationRequest);
  }

  @PutMapping
  public UserModel update(@RequestBody UserModel userModel) {return userService.update(userModel) ;}

  @GetMapping("{username}")
  public UserModel getByUsername(@PathVariable String username) { return userService.getByUsername(username);}
}
