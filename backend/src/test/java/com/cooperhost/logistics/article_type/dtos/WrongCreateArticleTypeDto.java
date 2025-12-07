/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperhost.logistics.article_type.dtos;

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
public class WrongCreateArticleTypeDto {
    private String name = "ArticleType";
    private String weight = "Test weight";
    private String volume = "Test volume";
}

