/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperhost.logistics.donation.dtos;

import java.util.List;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author cooper
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class WrongCreateDonationDto {
    @Valid()
    List<WrongUpsertArticleDto> articles = List.of(new WrongUpsertArticleDto());
}

