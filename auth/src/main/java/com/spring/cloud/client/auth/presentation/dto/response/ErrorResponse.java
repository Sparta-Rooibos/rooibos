package com.spring.cloud.client.auth.presentation.dto.response;

import com.spring.cloud.client.auth.application.exception.ErrorCodeProvider;
import org.springframework.http.ResponseEntity;

public record ErrorResponse(String errorCode, String message) {
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCodeProvider e) {
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorResponse(e.getCode(), e.getMessage()));
    }
}
