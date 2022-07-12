package com.example.auth.model.transactions;

import java.time.LocalDateTime;

public class Consumption {

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  Double quantity;

  LocalDateTime date;

}
