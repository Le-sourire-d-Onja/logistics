package com.cooperhost.logistics.donation.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author cooper
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class UpsertArticleDto {
  String id;

  @NotBlank()
  String typeId;

  @NotNull()
  Integer quantity;
}
