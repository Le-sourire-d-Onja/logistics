/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperhost.logistics.association.dtos;

import com.cooperhost.logistics.association.enums.AssociationType;
import com.cooperhost.logistics.shared.interfaces.OnCreate;
import com.cooperhost.logistics.shared.interfaces.OnUpdate;
import com.cooperhost.logistics.shared.interfaces.Phone;

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
    @Phone(groups = {OnCreate.class, OnUpdate.class})
    private String phone;

    @NotNull(groups = OnCreate.class)
    @Email(groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @NotNull(groups = OnCreate.class)
    private String description;
}
