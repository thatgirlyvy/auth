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
  private final SupplierRepository supplierRepository;

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

  public UserModel allocateOffer(String username, Offer offer) {

//    Offer gold = Offer.valueOf("PACK_GOLD");
//    Offer silver = Offer.valueOf("PACK_SILVER");
//    Offer bronze = Offer.valueOf("PACK_BRONZE");
//    Offer blue = Offer.valueOf("PACK_BLUE");
//    Offer orange = Offer.valueOf("PACK_ORANGE");

    UserModel userModel = userRepository.findByUsername(username);

    userModel.setOffer(offer);

//    if (offer == "PACK_GOLD")
//      userModel.setOffer(gold);
//    if ( offer == "PACK_SILVER")
//      userModel.setOffer(silver);
//    if ( offer == "PACK_BRONZE")
//      userModel.setOffer(bronze);
//    if ( offer == "PACK_BLUE")
//      userModel.setOffer(blue);
//    if ( offer == "PACK_ORANGE")
//      userModel.setOffer(orange);

    System.out.println(userModel);

    return userModel;
  }

}
