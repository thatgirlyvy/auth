package com.example.auth.model.authentication;

public class AuthenticationRequest {

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  private String username;

  private String password;

  public String getRole() {
    return role;
  }

  private String role;

  public String getFullName() {
    return fullName;
  }

  public String getEmail() {
    return email;
  }

  private String fullName;

  private String email;

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  private String firstName;

  private String lastName;

  public AuthenticationRequest() {

  }
}
