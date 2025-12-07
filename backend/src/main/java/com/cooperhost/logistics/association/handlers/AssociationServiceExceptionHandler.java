package com.cooperhost.logistics.association.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cooperhost.logistics.association.exception.AssociationAlreadyExists;
import com.cooperhost.logistics.shared.dtos.ErrorDto;
import com.cooperhost.logistics.shared.models.ApiResponse;

@RestControllerAdvice
public class AssociationServiceExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        List<ErrorDto> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDto errorDTO = new ErrorDto(error.getField(), error.getDefaultMessage());
                    errors.add(errorDTO);
                });
        serviceResponse.setStatus(HttpStatus.BAD_REQUEST);
        serviceResponse.setErrors(errors);
        return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AssociationAlreadyExists.class)
    public ResponseEntity<?> handleAssociationAlreadyExistsException(AssociationAlreadyExists exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus(HttpStatus.CONFLICT);
        serviceResponse.setErrors(Collections.singletonList(new ErrorDto("", exception.getMessage())));
        return new ResponseEntity<>(serviceResponse, HttpStatus.CONFLICT);
    }
}