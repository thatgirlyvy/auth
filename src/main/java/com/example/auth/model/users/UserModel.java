package com.example.auth.model.users;

import com.example.auth.model.transactions.Consumption;
import com.example.auth.model.transactions.Offer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public class UserModel {

  public void setId(String id) {
    this.id = id;
  }

  @Id
  private String id;

  public Offer getOffer() {
    return offer;
  }

  public void setOffer(Offer offer) {
    this.offer = offer;
  }

  private Offer offer;

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

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  private Double quantity;

  public List<Consumption> getConsumptions() {
    return consumptions;
  }

  public void setConsumptions(List<Consumption> consumptions) {
    this.consumptions = consumptions;
  }

  private List<Consumption> consumptions;
}
