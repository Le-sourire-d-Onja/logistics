/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperhost.logistics.donation.dtos;

import java.util.List;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author cooper
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class UpdateDonationDto {
    String description;

    @Valid()
    List<UpsertArticleDto> articles;
}
