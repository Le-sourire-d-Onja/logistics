/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cooperhost.logistics.association;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooperhost.logistics.association.dtos.AssociationDto;
import com.cooperhost.logistics.association.dtos.UpsertAssociationDto;
import com.cooperhost.logistics.shared.interfaces.OnCreate;
import com.cooperhost.logistics.shared.models.ApiResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 *
 * @author cooper
 */
@RestController
@RequestMapping("/api/associations")

public class AssociationController {

  @Autowired
  private AssociationService associationService;

  @PostMapping("/")
  public ResponseEntity<ApiResponse<AssociationDto>> create(@Validated(OnCreate.class) @RequestBody UpsertAssociationDto upsertAssociationDto) {
    AssociationDto associationDto = associationService.create(upsertAssociationDto);
    ApiResponse<AssociationDto> response = ApiResponse
      .<AssociationDto>builder()
      .status(HttpStatus.CREATED)
      .results(associationDto)
      .build();
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

}
