/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperhost.logistics.article_type.dtos;

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
    private Float weight;

    @NotNull()
    private Float volume;
}
