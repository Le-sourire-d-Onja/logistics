/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cooperhost.logistics.article_type.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooperhost.logistics.article_type.dtos.ArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.CreateArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.UpdateArticleTypeDto;
import com.cooperhost.logistics.article_type.services.ArticleTypeService;
import com.cooperhost.logistics.shared.models.ApiResponse;


/**
 *
 * @author cooper
 */
@RestController
@RequestMapping("/api/article-types")
public class ArticleTypeController {

  @Autowired
  private ArticleTypeService articleTypeService;

  @PostMapping()
  public ResponseEntity<ApiResponse<ArticleTypeDto>> create(@Validated() @RequestBody CreateArticleTypeDto createArticleTypeDto) {
    ArticleTypeDto articleTypeDto = this.articleTypeService.create(createArticleTypeDto);
    ApiResponse<ArticleTypeDto> response = ApiResponse
      .<ArticleTypeDto>builder()
      .status(HttpStatus.CREATED)
      .data(articleTypeDto)
      .build();
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<ApiResponse<List<ArticleTypeDto>>> findAll() {
    List<ArticleTypeDto> articleTypesDto = this.articleTypeService.findAll();
    ApiResponse<List<ArticleTypeDto>> response = ApiResponse
      .<List<ArticleTypeDto>>builder()
      .status(HttpStatus.OK)
      .data(articleTypesDto)
      .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<ArticleTypeDto>> update(
    @PathVariable() String id,
    @Validated() @RequestBody UpdateArticleTypeDto updateArticleTypeDto
  ) {
    ArticleTypeDto articleTypeDto = this.articleTypeService.update(id, updateArticleTypeDto);
    ApiResponse<ArticleTypeDto> response = ApiResponse
      .<ArticleTypeDto>builder()
      .status(HttpStatus.OK)
      .data(articleTypeDto)
      .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(
    @PathVariable() String id
  ) {
    this.articleTypeService.delete(id);
    ApiResponse<Void> response = ApiResponse
      .<Void>builder()
      .status(HttpStatus.NO_CONTENT)
      .data(null)
      .build();
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }
}
