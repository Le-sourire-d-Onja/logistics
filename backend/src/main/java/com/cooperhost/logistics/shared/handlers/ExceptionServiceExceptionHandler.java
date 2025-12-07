package com.cooperhost.logistics.shared.handlers;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.cooperhost.logistics.shared.dtos.ErrorDto;
import com.cooperhost.logistics.shared.models.ApiResponse;

@RestControllerAdvice
public class ExceptionServiceExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus(HttpStatus.valueOf(exception.getStatusCode().value()));
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto(exception.getMessage(), null)));
        return new ResponseEntity<>(serviceResponse, HttpStatus.valueOf(exception.getStatusCode().value()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto(exception.getMessage(), null)));
        return new ResponseEntity<>(serviceResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleServiceException(Exception exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto(exception.getMessage(), null)));
        return new ResponseEntity<>(serviceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}