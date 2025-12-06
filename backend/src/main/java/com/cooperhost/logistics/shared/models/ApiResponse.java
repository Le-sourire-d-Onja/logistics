package com.cooperhost.logistics.shared.models;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.cooperhost.logistics.shared.dtos.ErrorDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private HttpStatus status;
    private List<ErrorDto> errors;
    private T results;

}
