package com.cooperhost.logistics.article_type.exception;

public class ArticleTypeAlreadyExists extends RuntimeException {
  public ArticleTypeAlreadyExists() {
    super("The article type already exists");
  }
}
