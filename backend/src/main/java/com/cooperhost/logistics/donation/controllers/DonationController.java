/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cooperhost.logistics.donation.controllers;

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

import com.cooperhost.logistics.donation.dtos.CreateDonationDto;
import com.cooperhost.logistics.donation.dtos.DonationDto;
import com.cooperhost.logistics.donation.dtos.UpdateDonationDto;
import com.cooperhost.logistics.donation.services.DonationService;
import com.cooperhost.logistics.shared.models.ApiResponse;


/**
 *
 * @author cooper
 */
@RestController
@RequestMapping("/api/donations")
public class DonationController {

  @Autowired
  private DonationService donationService;

  @PostMapping()
  public ResponseEntity<ApiResponse<DonationDto>> create(@Validated() @RequestBody CreateDonationDto createDonationDto) {
    DonationDto donationDto = this.donationService.create(createDonationDto);
    ApiResponse<DonationDto> response = ApiResponse
      .<DonationDto>builder()
      .status(HttpStatus.CREATED)
      .data(donationDto)
      .build();
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<ApiResponse<List<DonationDto>>> findAll() {
    List<DonationDto> donationsDto = this.donationService.findAll();
    ApiResponse<List<DonationDto>> response = ApiResponse
      .<List<DonationDto>>builder()
      .status(HttpStatus.OK)
      .data(donationsDto)
      .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<DonationDto>> update(
    @PathVariable() String id,
    @Validated() @RequestBody UpdateDonationDto updateDonationDto
  ) {
    DonationDto donationDto = this.donationService.update(id, updateDonationDto);
    ApiResponse<DonationDto> response = ApiResponse
      .<DonationDto>builder()
      .status(HttpStatus.OK)
      .data(donationDto)
      .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(
    @PathVariable() String id
  ) {
    this.donationService.delete(id);
    ApiResponse<Void> response = ApiResponse
      .<Void>builder()
      .status(HttpStatus.NO_CONTENT)
      .data(null)
      .build();
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }
 
}
