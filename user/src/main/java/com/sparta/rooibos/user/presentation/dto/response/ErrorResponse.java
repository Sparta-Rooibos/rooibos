package com.sparta.rooibos.user.presentation.dto.response;

import com.sparta.rooibos.user.application.exception.ErrorCodeProvider;
import org.springframework.http.ResponseEntity;

public record ErrorResponse(String errorCode, String message) {
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCodeProvider e) {
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorResponse(e.getCode(), e.getMessage()));
    }
}
