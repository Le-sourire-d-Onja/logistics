/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperhost.logistics.donation.dtos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
public class CreateDonationDto {
    @NotNull()
    String description;

    @NotEmpty()
    @Valid()
    List<UpsertArticleDto> articles;
}
