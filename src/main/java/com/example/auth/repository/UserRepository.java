package com.example.auth.repository;

import com.example.auth.model.users.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
  UserModel findByUsername(String username);
  UserModel getById(String id);
}
