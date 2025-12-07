package com.cooperhost.logistics.donation.dtos;

import com.cooperhost.logistics.article_type.dtos.ArticleTypeDto;

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
public class ArticleDto {
  String id;
  ArticleTypeDto type;
  Integer quantity;
}
