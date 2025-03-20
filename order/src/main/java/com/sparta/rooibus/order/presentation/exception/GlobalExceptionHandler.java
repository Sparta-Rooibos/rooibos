package com.sparta.rooibus.order.presentation.exception;

import com.sparta.rooibus.order.application.exception.BusinessOrderException;
import com.sparta.rooibus.order.application.exception.custom.OrderErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessOrderException.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("[orderException] error :", e);
        return new ResponseEntity<>(new ErrorResponse(OrderErrorCode.ORDER_NOT_FOUND.getCode(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

        private record ErrorResponse(String errorCode, String message) {
    }
}
