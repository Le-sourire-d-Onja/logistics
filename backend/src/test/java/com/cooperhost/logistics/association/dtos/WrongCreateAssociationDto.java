/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperhost.logistics.association.dtos;

import com.cooperhost.logistics.association.enums.AssociationType;
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
public class WrongCreateAssociationDto {
    private Integer name = 1;
    private AssociationType type = AssociationType.ASSOCIATION;
    private String personInCharge = "test";
    private String phone = "assas10101";
    private String email = "wrong";
    private String description = "Ceci est une description";
}

