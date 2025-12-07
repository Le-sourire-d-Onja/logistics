package com.cooperhost.logistics.donation.models;

import com.cooperhost.logistics.article_type.models.ArticleTypeEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author cooper
 */
@Entity
@Table(name = "ARTICLES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @ManyToOne
  ArticleTypeEntity type;

  Integer quantity;
}
