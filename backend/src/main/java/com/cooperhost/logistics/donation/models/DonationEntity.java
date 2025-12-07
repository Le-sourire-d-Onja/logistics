package com.cooperhost.logistics.donation.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author cooper
 */
@Entity
@Table(name = "DONATIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  String description;

  @CreatedDate
  Instant createdAt;

  @LastModifiedDate
  Instant updatedAt;

  @OneToMany
  List<ArticleEntity> articles = new ArrayList<>();
}
