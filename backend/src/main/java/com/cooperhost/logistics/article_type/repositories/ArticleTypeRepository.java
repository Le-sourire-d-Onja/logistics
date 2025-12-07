package com.cooperhost.logistics.article_type.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cooperhost.logistics.article_type.models.ArticleTypeEntity;

public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity, String> {

  boolean existsByName(String name);
}
