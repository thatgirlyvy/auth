package com.example.auth.service;

import com.example.auth.model.Offer;
import com.example.auth.model.UserModel;
import com.example.auth.model.dto.UserFilterDTO;
import com.example.auth.model.dto.UserPaginatedDTO;
import com.example.auth.repository.SupplierRepository;
import com.example.auth.repository.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

  private final UserRepository userRepository;
  private final SupplierRepository supplierRepository; // tu ne l'utilise pas ?

  public SupplierService(UserRepository userRepository, SupplierRepository supplierRepository){
    this.supplierRepository = supplierRepository;
    this.userRepository = userRepository;
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

  // l'offre doit être en string afin de la sérialiser sans problème au niveau du controleur
  public UserModel allocateOffer(String username, String offer) {

    UserModel userModel = userRepository.findByUsername(username);

    Offer offerValue = Offer.valueOf(offer);

    userModel.setOffer(offerValue);

    userRepository.save(userModel); // ne pas oublier de sauvergarder

    return userModel;
  }

}
