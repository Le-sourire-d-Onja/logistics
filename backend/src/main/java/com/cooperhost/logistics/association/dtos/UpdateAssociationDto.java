/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperhost.logistics.association.dtos;

import com.cooperhost.logistics.association.enums.AssociationType;
import com.cooperhost.logistics.shared.interfaces.Phone;

import jakarta.validation.constraints.Email;
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
public class UpdateAssociationDto {
    private String name;

    private AssociationType type;

    private String personInCharge;

    private String address;

    @Phone()
    private String phone;

    @Email()
    private String email;

    private String description;
}
