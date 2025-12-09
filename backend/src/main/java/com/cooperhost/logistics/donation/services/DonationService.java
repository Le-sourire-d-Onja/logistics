/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cooperhost.logistics.donation.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperhost.logistics.donation.dtos.CreateDonationDto;
import com.cooperhost.logistics.donation.dtos.DonationDto;
import com.cooperhost.logistics.donation.dtos.UpdateDonationDto;
import com.cooperhost.logistics.donation.exception.DonationAlreadyExists;
import com.cooperhost.logistics.donation.exception.DonationNotFound;
import com.cooperhost.logistics.donation.models.DonationEntity;
import com.cooperhost.logistics.donation.repositories.DonationRepository;

/**
 *
 * @author cooper
 */
@Service
public class DonationService {

  @Autowired
  private DonationRepository donationRepository;

  @Autowired
  private ModelMapper modelMapper;


  public DonationDto create(CreateDonationDto createDonationDto) throws DonationAlreadyExists {
    DonationEntity createDonation = this.modelMapper.map(createDonationDto, DonationEntity.class);
    DonationEntity donation = donationRepository.save(createDonation);
    return this.modelMapper.map(donation, DonationDto.class);
  }


  public List<DonationDto> findAll() {
    List<DonationEntity> donations = donationRepository.findAll();
    return donations.stream().map((donation) ->
      this.modelMapper.map(donation, DonationDto.class)
    ).toList();
  }

  public DonationDto update(String id, UpdateDonationDto updateDonationDto) throws DonationNotFound {
    DonationEntity existing = donationRepository.findById(id)
      .orElseThrow(DonationNotFound::new);
    this.modelMapper.map(updateDonationDto, existing);
    existing.setId(id);
    DonationEntity donation = donationRepository.save(existing);
    return this.modelMapper.map(donation, DonationDto.class);
  }

  public void delete(String id) {
    if (!donationRepository.existsById(id)) {
      throw new DonationNotFound();
    }
    donationRepository.deleteById(id);
  }
}
