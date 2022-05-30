package com.example.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserModel {

  @Id
  private String id;

  public UserModel() {

  }

  public String getUsername() {
    return username;
  }

  public String getId() {
    return id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  private String username;

  private String password;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  private String role;

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  private String fullName;

  private String email;
}
