package com.sparta.rooibus.delivery.presentation.exception;

import com.sparta.rooibus.delivery.application.exception.BusinessOrderException;
import com.sparta.rooibus.delivery.application.exception.custom.DeliveryError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessOrderException.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("[orderException] error :", e);
        return new ResponseEntity<>(new ErrorResponse(DeliveryError.DELIVERY_NOT_FOUND.getCode(), e.getMessage()), DeliveryError.DELIVERY_NOT_FOUND.getStatus());
    }
    private record ErrorResponse(String errorCode, String message) {
    }
}
