/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperhost.logistics.association.dtos;

import com.cooperhost.logistics.association.enums.AssociationType;
import com.cooperhost.logistics.shared.interfaces.Phone;

import jakarta.validation.constraints.Email;
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
public class CreateArticleTypeDto {
    @NotBlank()
    private String name;

    @NotNull()
    private AssociationType type;

    @NotBlank()
    private String personInCharge;

    @NotBlank()
    private String address;

    @NotNull()
    @Phone()
    private String phone;

    @NotNull()
    @Email()
    private String email;

    @NotNull()
    private String description;
}
