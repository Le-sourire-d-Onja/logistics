package com.cooperhost.logistics.shared.handlers;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cooperhost.logistics.shared.dtos.ErrorDto;
import com.cooperhost.logistics.shared.models.ApiResponse;

@RestControllerAdvice
public class ExceptionServiceExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleServiceException(Exception exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto("", exception.getMessage())));
        return new ResponseEntity<>(serviceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}