package com.sparta.rooibos.order.presentation.exception;

import com.sparta.rooibos.order.application.exception.BusinessOrderException;
import com.sparta.rooibos.order.application.exception.custom.OrderErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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
