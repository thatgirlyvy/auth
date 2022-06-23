package com.example.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.auth.model.Supplier;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
  Supplier findByUsername(String username);
}
