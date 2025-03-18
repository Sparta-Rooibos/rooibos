package com.sparta.rooibos.product.presentation.global.exception;

import com.sparta.rooibos.product.application.exception.BusinessProductException;
import com.sparta.rooibos.product.application.exception.ProductErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Comparator;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 그외 exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("[handleException] error :", e);
        return new ResponseEntity<>(new ErrorResponse(ProductErrorCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // sqlException처리
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<?> handleSqlBadRequestException(BadSqlGrammarException e) {
        log.error("[handleSqlBadRequestException] error :", e);
        return new ResponseEntity<>(new ErrorResponse(ProductErrorCode.SQL_ERROR.getCode(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // 공통 에러처리
    // json이 틀렸을 경우
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("[handleHttpMessageNotReadableException] error :", e);
        return new ResponseEntity<>(new ErrorResponse(ProductErrorCode.JSON_PARSING_ERROR.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    // 리소스가 존재하지 않는경우
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoResourceFoundException e) {
        log.error("[handleNoHandlerFoundException] error :", e);
        return new ResponseEntity<>(new ErrorResponse(ProductErrorCode.RESOURCE_NOT_FOUND.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(MethodArgumentNotValidException e) {
        log.error("[handleHttpMessageNotReadableException] error :", e);
        final FieldError fieldError = e.getFieldErrors()
                .stream().sorted(Comparator.comparing(FieldError::getField))
                .min(Comparator.comparing(DefaultMessageSourceResolvable::getDefaultMessage))
                .get();
        return new ResponseEntity<>(new ErrorResponse(ProductErrorCode.INVALID_CODE.getCode(), fieldError.getField() + ": " + fieldError.getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }
    // 비즈니스 예외
    @ExceptionHandler(BusinessProductException.class)
    public ResponseEntity<?> handleProductException(BusinessProductException e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }
    @Getter
    private static class ErrorResponse {
        private final String errorCode;
        private final String message;
        public ErrorResponse(String errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }
    }
}
