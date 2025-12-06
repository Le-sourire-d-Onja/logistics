/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperhost.logistics.association.dtos;

import com.cooperhost.logistics.association.enums.AssociationType;
import com.cooperhost.logistics.shared.interfaces.OnCreate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpsertAssociationDto {
    @NotBlank(groups = OnCreate.class)
    private String name;

    @NotNull(groups = OnCreate.class)
    private AssociationType type;

    @NotBlank(groups = OnCreate.class)
    private String personInCharge;

    @NotBlank(groups = OnCreate.class)
    private String address;

    @NotNull(groups = OnCreate.class)
    private String phone;

    @Email()
    @NotNull(groups = OnCreate.class)
    private String email;

    @NotNull(groups = OnCreate.class)
    private String description;
}
