package com.cooperhost.logistics.article_type.dtos;

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
public class ArticleTypeDto {
  String id;
  String name;
  Float weight;
  Float volume;
}
