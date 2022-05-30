package com.example.auth.model;

public class AuthenticationResponse {

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  private String response;

  public AuthenticationResponse() {

  }

  public AuthenticationResponse(String response) {
    this.response = response;
  }
}
