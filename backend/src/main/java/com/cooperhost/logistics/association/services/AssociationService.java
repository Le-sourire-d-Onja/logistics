/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cooperhost.logistics.association.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperhost.logistics.association.dtos.AssociationDto;
import com.cooperhost.logistics.association.dtos.UpsertAssociationDto;
import com.cooperhost.logistics.association.exception.AssociationAlreadyExists;
import com.cooperhost.logistics.association.models.AssociationEntity;
import com.cooperhost.logistics.association.repositories.AssociationRepository;

/**
 *
 * @author cooper
 */
@Service
public class AssociationService {

  @Autowired
  private AssociationRepository associationRepository;

  @Autowired
  private ModelMapper modelMapper;


  public AssociationDto create(UpsertAssociationDto upsertAssociationDto) throws AssociationAlreadyExists {
    AssociationEntity upsertAssociation = this.modelMapper.map(upsertAssociationDto, AssociationEntity.class);
    if (associationRepository.existsByName(upsertAssociation.getName())) {
      throw new AssociationAlreadyExists();
    }
    AssociationEntity association = associationRepository.save(upsertAssociation);
    return this.modelMapper.map(association, AssociationDto.class);
  }

}
