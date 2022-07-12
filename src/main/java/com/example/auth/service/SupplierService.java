package com.example.auth.service;

import com.example.auth.configuration.security.JWTSingleton;
import com.example.auth.model.authentication.AuthenticationRequest;
import com.example.auth.model.authentication.AuthenticationResponse;
import com.example.auth.model.transactions.Offer;
import com.example.auth.model.users.Supplier;
import com.example.auth.model.users.UserModel;
import com.example.auth.model.dto.UserFilterDTO;
import com.example.auth.model.dto.UserPaginatedDTO;
import com.example.auth.repository.SupplierRepository;
import com.example.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SupplierService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SupplierService.class);

  private final UserRepository userRepository;
  private final SupplierRepository supplierRepository;
  private final AuthenticationManager authenticationManager;
  private final SupplierAuthentication supplierAuthentication;

  public SupplierService(UserRepository userRepository, SupplierRepository supplierRepository, AuthenticationManager authenticationManager, SupplierAuthentication supplierAuthentication){
    this.supplierRepository = supplierRepository;
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.supplierAuthentication = supplierAuthentication;
  }

  public ResponseEntity<AuthenticationResponse> create(AuthenticationRequest authenticationRequest){
    String username = authenticationRequest.getUsername();
    String firstName = authenticationRequest.getFirstName();
    String lastName = authenticationRequest.getLastName();
    String email = authenticationRequest.getEmail();
    String password = authenticationRequest.getPassword();

    Supplier supplier = new Supplier();
    supplier.setUsername(username);
    supplier.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    supplier.setFirstName(firstName);
    supplier.setLastName(lastName);
    supplier.setEmail(email);
    supplier.setRole("supplier");

    try {
      supplierRepository.save(supplier);
    } catch (Exception e){
      return ResponseEntity.ok(new AuthenticationResponse("error during supplier subscription for: " + username));
    }

    return ResponseEntity.ok(new AuthenticationResponse("successful subscription for: " + username));
  }

  public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest){
    String username = authenticationRequest.getUsername();
    String password = authenticationRequest.getPassword();

    try{
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (Exception e){

      LOGGER.error(e.getMessage());

      return ResponseEntity.ok(new AuthenticationResponse("error during authentication for: " + username));
    }

    UserDetails loadedUser = supplierAuthentication.loadUserByUsername(username);

    String generatedToken = JWTSingleton.getInstance(loadedUser.getUsername(), new HashMap<>()).getToken();

    return ResponseEntity.ok()
      .header("Authorization", generatedToken)
      .header("Access-Control-Expose-Headers", "Authorization")
      .body(new AuthenticationResponse());
  }

  public UserPaginatedDTO find(int page, int size, String field, UserFilterDTO dto){
    // Dans le cas où nous souhaitons avoir toutes les données dans la BD.
    if (dto.getUsername() == null && dto.getFullName() == null && dto.getOffer() == null && dto.getEmail() == null ){
      Page<UserModel> pageFilter = userRepository.findAll(PageRequest.of(page, size, Sort.by(field)));
      return new UserPaginatedDTO(pageFilter.getContent(), pageFilter.getTotalElements()) ;
    }

    // Dans le cas où nous souhaitons appliquer un filtre sur les données à rechercher.
    UserModel userModel = new UserModel();
    userModel.setUsername(dto.getUsername());
    userModel.setFullName(dto.getFullName());
    userModel.setOffer(dto.getOffer());
    userModel.setEmail(dto.getEmail());

    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withIgnoreCase().withIgnoreNullValues();

    Example<UserModel> example = Example.of(userModel, exampleMatcher);

    Page<UserModel> pageFind = userRepository.findAll(example, PageRequest.of(page, size, Sort.by(field)));

    return new UserPaginatedDTO(pageFind.getContent(), pageFind.getTotalElements()) ;
  }

  public UserModel allocateOffer(String username, String offer) {

    UserModel userModel = userRepository.findByUsername(username);
    Offer offer1 = Offer.valueOf(offer);

    userModel.setOffer(offer1);

    userRepository.save(userModel);

    return userModel;
  }

  public UserModel updateUser(UserModel userModel){
    UserModel user1 = userRepository.findByUsername(userModel.getUsername());

    LOGGER.info("{}", userModel);

    if(user1 == null) {
      LOGGER.error("User not found");
      return userRepository.save(userModel);
    } else {
      user1.setFullName(userModel.getFullName());
      user1.setUsername(userModel.getUsername());
      user1.setEmail(userModel.getEmail());
      user1.setOffer(userModel.getOffer());
      userRepository.save(user1);
    }
    return userModel;
  }

  public boolean deleteUser(String id) {
    UserModel user1 = userRepository.getById(id);
    if(user1 != null) {
      userRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public Supplier getByUsername(String username){ return supplierRepository.findByUsername(username);}

}
