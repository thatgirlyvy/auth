package com.example.auth.repository;

import com.example.auth.model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierRepository extends MongoRepository<Supplier, String> {

  Supplier findByUsername(String username);
}
