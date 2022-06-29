package com.example.auth.service;

import com.example.auth.model.users.Supplier;
import com.example.auth.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SupplierAuthentication implements UserDetailsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SupplierAuthentication.class);

  private final SupplierRepository supplierRepository;

  public SupplierAuthentication(SupplierRepository supplierRepository) {
    this.supplierRepository = supplierRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Supplier foundedUser = supplierRepository.findByUsername(username);

    if (foundedUser == null)
      throw new UsernameNotFoundException("Username not found");

    LOGGER.info("Success");

    String name = foundedUser.getUsername();
    String pwd = foundedUser.getPassword();

    return new User(name, pwd, new ArrayList<>());
  }
}
