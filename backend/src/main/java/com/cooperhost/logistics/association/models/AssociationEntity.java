package com.cooperhost.logistics.association.models;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.cooperhost.logistics.association.enums.AssociationType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author cooper
 */
@Entity
@Table(name = "ASSOCIATIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;
  @Column(unique=true)
  String name;
  AssociationType type;
  String personInCharge;
  String address;
  String email;
  String phone;
  String description;
  @CreatedDate
  Instant createdAt;
  @LastModifiedDate
  Instant updatedAt;
}
