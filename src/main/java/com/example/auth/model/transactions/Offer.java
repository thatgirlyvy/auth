package com.example.auth.model.transactions;

public enum Offer {

  PACK_GOLD("Pack Gold", 10000, "FAC 52554", 94000),
  PACK_SILVER("Pack Silver", 5000, "FAC 52557", 47000),
  PACK_BRONZE("Pack Bronze", 3000, "FAC 52787", 28000),
  PACK_BLUE("Pack Blue", 1000, "FAC 52577", 9400),
  PACK_ORANGE("Pack Orange", 500, "FAC 57887", 8900);

  public String getName() {
    return name;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public String getReference() {
    return reference;
  }

  private String name;
  private Integer quantity;
  private String reference;

  public Integer getPrice() {
    return price;
  }

  private Integer price;

  Offer(String name, Integer quantity, String reference, Integer price){
    this.name = name;
    this.quantity = quantity;
    this.reference = reference;
    this.price = price;
  }

}
