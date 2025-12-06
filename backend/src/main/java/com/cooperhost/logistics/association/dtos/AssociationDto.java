package com.cooperhost.logistics.association.dtos;

import com.cooperhost.logistics.association.enums.AssociationType;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author cooper
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AssociationDto {
  String id;
  @Column(unique=true)
  String name;
  AssociationType type;
  String personInCharge;
  String address;
  String email;
  String phone;
  String description;

}
