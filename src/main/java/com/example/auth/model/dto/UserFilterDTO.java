package com.example.auth.model.dto;

import com.example.auth.model.transactions.Offer;

public class UserFilterDTO {

  public String getUsername() {
    return username;
  }

  public String getFullName() {
    return fullName;
  }

  public Offer getOffer() {
    return offer;
  }

  public String getEmail() {
    return email;
  }

  private String username;

  private String fullName;

  private Offer offer;

  private String email;

  public UserFilterDTO() {

  }

  public UserFilterDTO(String username, String fullName, Offer offer, String email){
    this.username = username;
    this.fullName = fullName;
    this.offer = offer;
    this.email = email;
  }
}
