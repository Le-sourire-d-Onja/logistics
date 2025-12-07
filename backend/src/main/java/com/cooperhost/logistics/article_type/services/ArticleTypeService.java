/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cooperhost.logistics.article_type.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperhost.logistics.article_type.exception.ArticleTypeAlreadyExists;
import com.cooperhost.logistics.article_type.dtos.ArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.CreateArticleTypeDto;
import com.cooperhost.logistics.article_type.dtos.UpdateArticleTypeDto;
import com.cooperhost.logistics.article_type.exception.ArticleTypeNotFound;
import com.cooperhost.logistics.article_type.models.ArticleTypeEntity;
import com.cooperhost.logistics.article_type.repositories.ArticleTypeRepository;

/**
 *
 * @author cooper
 */
@Service
public class ArticleTypeService {

  @Autowired
  private ArticleTypeRepository articleTypeRepository;

  @Autowired
  private ModelMapper modelMapper;


  public ArticleTypeDto create(CreateArticleTypeDto createArticleTypeDto) throws ArticleTypeAlreadyExists {
    ArticleTypeEntity createArticleType = this.modelMapper.map(createArticleTypeDto, ArticleTypeEntity.class);
    if (articleTypeRepository.existsByName(createArticleType.getName())) {
      throw new ArticleTypeAlreadyExists();
    }
    ArticleTypeEntity articleType = articleTypeRepository.save(createArticleType);
    return this.modelMapper.map(articleType, ArticleTypeDto.class);
  }


  public List<ArticleTypeDto> findAll() {
    List<ArticleTypeEntity> articleTypes = articleTypeRepository.findAll();
    return articleTypes.stream().map((articleType) ->
      this.modelMapper.map(articleType, ArticleTypeDto.class)
    ).toList();
  }

  public ArticleTypeDto update(String id, UpdateArticleTypeDto updateArticleTypeDto) throws ArticleTypeNotFound {
    ArticleTypeEntity existing = articleTypeRepository.findById(id)
      .orElseThrow(ArticleTypeNotFound::new);
    if (articleTypeRepository.existsByName(id)) {
      throw new ArticleTypeAlreadyExists();
    }
    this.modelMapper.map(updateArticleTypeDto, existing);
    existing.setId(id);
    ArticleTypeEntity articleType = articleTypeRepository.save(existing);
    return this.modelMapper.map(articleType, ArticleTypeDto.class);
  }

  public void delete(String id) {
    if (!articleTypeRepository.existsById(id)) {
      throw new ArticleTypeNotFound();
    }
    articleTypeRepository.deleteById(id);
  }
}
