package com.sparta.rooibus.order.presentation.exception;

import com.sparta.rooibus.order.application.exception.BusinessOrderException;
import com.sparta.rooibus.order.application.exception.custom.OrderErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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
        return new ResponseEntity<>(new ErrorResponse(OrderErrorCode.ORDER_NOT_FOUND.getCode(), e.getMessage()), OrderErrorCode.ORDER_NOT_FOUND.getStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.error("[orderException] error :", e);
        return new ResponseEntity<>(new ErrorResponse(OrderErrorCode.BAD_REQUEST_ERROR.getCode(), e.getMessage()), OrderErrorCode.BAD_REQUEST_ERROR.getStatus());
    }

        private record ErrorResponse(String errorCode, String message) {
    }
}
