package com.example.auth.repository;

import com.example.auth.model.Offer;
import com.example.auth.model.UserModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserCustomRepository {

  List<UserModel> findUserByProperties(String username, String fullName, Offer offer, String email, Pageable pageable);
}
