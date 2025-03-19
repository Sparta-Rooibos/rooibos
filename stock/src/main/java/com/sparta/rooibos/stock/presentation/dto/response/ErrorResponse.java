package com.sparta.rooibos.stock.presentation.dto.response;

import com.sparta.rooibos.stock.application.custom.CommonErrorCode;
import com.sparta.rooibos.stock.application.exception.ErrorCodeProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ErrorResponse(String errorCode, String message) {
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCodeProvider e) {
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(CommonErrorCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage()));
    }
}