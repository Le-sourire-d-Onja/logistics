/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cooperhost.logistics.association.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperhost.logistics.association.dtos.AssociationDto;
import com.cooperhost.logistics.association.dtos.CreateAssociationDto;
import com.cooperhost.logistics.association.dtos.UpdateAssociationDto;
import com.cooperhost.logistics.association.exception.AssociationAlreadyExists;
import com.cooperhost.logistics.association.exception.AssociationNotFound;
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


  public AssociationDto create(CreateAssociationDto createAssociationDto) throws AssociationAlreadyExists {
    AssociationEntity createAssociation = this.modelMapper.map(createAssociationDto, AssociationEntity.class);
    if (associationRepository.existsByName(createAssociation.getName())) {
      throw new AssociationAlreadyExists();
    }
    AssociationEntity association = associationRepository.save(createAssociation);
    return this.modelMapper.map(association, AssociationDto.class);
  }


  public List<AssociationDto> findAll() {
    List<AssociationEntity> associations = associationRepository.findAll();
    return associations.stream().map((association) -> 
      this.modelMapper.map(association, AssociationDto.class)
    ).toList();
  }

  public AssociationDto update(String id, UpdateAssociationDto updateAssociationDto) throws AssociationNotFound {
    if (!associationRepository.existsById(id)) {
      throw new AssociationNotFound();
    }
    if (associationRepository.existsByName(id)) {
      throw new AssociationAlreadyExists();
    }
    AssociationEntity updateAssociation = this.modelMapper.map(updateAssociationDto, AssociationEntity.class);
    updateAssociation.setId(id);
    AssociationEntity association = associationRepository.save(updateAssociation);
    return this.modelMapper.map(association, AssociationDto.class);
  }

  public void delete(String id) {
    if (!associationRepository.existsById(id)) {
      throw new AssociationNotFound();
    }
    associationRepository.deleteById(id);
  }
}
