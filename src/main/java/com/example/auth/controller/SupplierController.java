package com.example.auth.controller;

import com.example.auth.model.authentication.AuthenticationRequest;
import com.example.auth.model.authentication.AuthenticationResponse;
import com.example.auth.model.transactions.Offer;
import com.example.auth.model.users.Supplier;
import com.example.auth.model.users.UserModel;
import com.example.auth.model.dto.UserFilterDTO;
import com.example.auth.model.dto.UserPaginatedDTO;
import com.example.auth.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("supplier")
public class SupplierController {

  private final SupplierService supplierService;

  public SupplierController(SupplierService supplierService){
    this.supplierService = supplierService;
  }

  @PostMapping("/create")
  public ResponseEntity<AuthenticationResponse> subscribeSupplier(@RequestBody AuthenticationRequest authenticationRequest){
    return supplierService.create(authenticationRequest);
  }

  @PostMapping("/auth")
  public ResponseEntity<AuthenticationResponse> authenticateSupplier(@RequestBody AuthenticationRequest authenticationRequest){
    return supplierService.authenticate(authenticationRequest);
  }

  @GetMapping("/{username}")
  public Supplier getByUsername(@PathVariable String username) { return supplierService.getByUsername(username);}

  @GetMapping
  public ResponseEntity<UserPaginatedDTO> list(@RequestParam(required = false) String username, @RequestParam(required = false) String fullName,
                                               @RequestParam(required = false) String offer, @RequestParam(required = false) String email,
                                               @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size,
                                               @RequestParam(defaultValue = "id") String field){

    Offer off = Offer.valueOf(offer);
    UserFilterDTO dto = new UserFilterDTO(username, fullName, off, email);

    UserPaginatedDTO users = supplierService.find(page, size, field, dto);

    return ResponseEntity.ok(users);
  }

  @PostMapping("{username}/allocate")
  public UserModel allocate(@PathVariable String username, @RequestParam String offer){
    return supplierService.allocateOffer(username, offer);
  }
}
