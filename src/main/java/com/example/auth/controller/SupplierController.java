package com.example.auth.controller;

import com.example.auth.model.Offer;
import com.example.auth.model.UserModel;
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

  @GetMapping
  public ResponseEntity<UserPaginatedDTO> list(@RequestParam(required = false) String username, @RequestParam(required = false) String fullName,
                                               @RequestParam(required = false) Offer offer, @RequestParam(required = false) String email,
                                               @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size,
                                               @RequestParam(defaultValue = "id") String field){

    UserFilterDTO dto = new UserFilterDTO(username, fullName, offer, email);

    UserPaginatedDTO users = supplierService.find(page, size, field, dto);

    return ResponseEntity.ok(users);
  }

  @PostMapping("{username}/allocate")
  public UserModel allocate(@PathVariable String username, @RequestParam String offer){
    return supplierService.allocateOffer(username, offer);
  }
}
