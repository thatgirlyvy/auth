package com.example.auth.model;

public enum Offer {

  PACK_GOLD("Pack Gold", 10000, Constant.REF, 94000),
  PACK_SILVER("Pack Silver", 5000, Constant.REF, 47000),
  PACK_BRONZE("Pack Bronze", 3000, Constant.REF, 28000),
  PACK_BLUE("Pack Blue", 1000, Constant.REF, 9400),
  PACK_ORANGE("Pack Orange", 500, Constant.REF, 8900);

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
