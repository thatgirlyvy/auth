package com.example.auth.model.dto;

import com.example.auth.model.users.UserModel;

import java.util.List;

public class UserPaginatedDTO {

  public List<UserModel> getData() {
    return data;
  }

  public void setData(List<UserModel> data) {
    this.data = data;
  }

  public Long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }

  private List<UserModel> data;

  private Long totalElements;

  public UserPaginatedDTO(List<UserModel> data, Long totalElements){
    this.data = data;
    this.totalElements = totalElements;
  }
}
