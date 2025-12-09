package com.cooperhost.logistics.donation.dtos;


import java.util.ArrayList;
import java.util.List;

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
public class DonationDto {
  String id;
  String description;
  List<ArticleDto> articles = new ArrayList<>();
}
