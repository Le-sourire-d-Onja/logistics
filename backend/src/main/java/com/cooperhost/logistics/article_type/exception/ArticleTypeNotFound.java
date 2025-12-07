package com.cooperhost.logistics.article_type.exception;

public class ArticleTypeNotFound extends RuntimeException {
  public ArticleTypeNotFound() {
    super("The article type is not found");
  }
}
