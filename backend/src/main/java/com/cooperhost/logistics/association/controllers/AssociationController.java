/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cooperhost.logistics.association.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooperhost.logistics.association.dtos.AssociationDto;
import com.cooperhost.logistics.association.dtos.CreateAssociationDto;
import com.cooperhost.logistics.association.dtos.UpdateAssociationDto;
import com.cooperhost.logistics.association.services.AssociationService;
import com.cooperhost.logistics.shared.models.ApiResponse;


/**
 *
 * @author cooper
 */
@RestController
@RequestMapping("/api/associations")
public class AssociationController {

  @Autowired
  private AssociationService associationService;

  @PostMapping()
  public ResponseEntity<ApiResponse<AssociationDto>> create(@Validated() @RequestBody CreateAssociationDto createAssociationDto) {
    AssociationDto associationDto = this.associationService.create(createAssociationDto);
    ApiResponse<AssociationDto> response = ApiResponse
      .<AssociationDto>builder()
      .status(HttpStatus.CREATED)
      .data(associationDto)
      .build();
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<ApiResponse<List<AssociationDto>>> findAll() {
    List<AssociationDto> associationsDto = this.associationService.findAll();
    ApiResponse<List<AssociationDto>> response = ApiResponse
      .<List<AssociationDto>>builder()
      .status(HttpStatus.OK)
      .data(associationsDto)
      .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<AssociationDto>> update(
    @PathVariable("id") String id,
    @Validated() @RequestBody UpdateAssociationDto updateAssociationDto
  ) {
    AssociationDto associationDto = this.associationService.update(id, updateAssociationDto);
    ApiResponse<AssociationDto> response = ApiResponse
      .<AssociationDto>builder()
      .status(HttpStatus.OK)
      .data(associationDto)
      .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(
    @PathVariable("id") String id
  ) {
    this.associationService.delete(id);
    ApiResponse<Void> response = ApiResponse
      .<Void>builder()
      .status(HttpStatus.NO_CONTENT)
      .data(null)
      .build();
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }
 
}
