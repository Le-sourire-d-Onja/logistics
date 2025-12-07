package com.cooperhost.logistics.article_type.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cooperhost.logistics.article_type.exception.ArticleTypeAlreadyExists;
import com.cooperhost.logistics.article_type.exception.ArticleTypeNotFound;
import com.cooperhost.logistics.shared.dtos.ErrorDto;
import com.cooperhost.logistics.shared.models.ApiResponse;

@RestControllerAdvice
public class ArticleTypeServiceExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        List<ErrorDto> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDto errorDTO = new ErrorDto(error.getDefaultMessage(), Optional.of(error.getField()));
                    errors.add(errorDTO);
                });
        serviceResponse.setStatus(HttpStatus.BAD_REQUEST);
        serviceResponse.setErrors(errors);
        return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArticleTypeAlreadyExists.class)
    public ResponseEntity<?> handleAssociationAlreadyExistsException(ArticleTypeAlreadyExists exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus(HttpStatus.CONFLICT);
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto(exception.getMessage(), null)));
        return new ResponseEntity<>(serviceResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ArticleTypeNotFound.class)
    public ResponseEntity<?> handleAssociationNotFoundException(ArticleTypeNotFound exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus(HttpStatus.NOT_FOUND);
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto(exception.getMessage(), null)));
        return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
    }
}