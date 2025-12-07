package com.cooperhost.logistics.article_type.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author cooper
 */
@Entity
@Table(name = "ARTICLE_TYPES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTypeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;
  @Column(unique=true)
  String name;
  Float weight;
  Float volume;
}
