package com.sparta.rooibos.auth.presentation.controller;

import com.sparta.rooibos.auth.application.exception.BusinessAuthException;
import com.sparta.rooibos.auth.application.exception.custom.CommonErrorCode;
import com.sparta.rooibos.auth.presentation.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 그외 exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("error :", e);
        return ErrorResponse.toResponseEntity(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    // sql exception처리
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<ErrorResponse> handleSqlBadRequestException(BadSqlGrammarException e) {
        log.warn("error :", e);
        return ErrorResponse.toResponseEntity(CommonErrorCode.SQL_ERROR);
    }

    // 공통 에러처리
    // json이 틀렸을 경우
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("error :", e);
        return ErrorResponse.toResponseEntity(CommonErrorCode.JSON_PARSING_ERROR);
    }

    // 비즈니스 예외
    @ExceptionHandler(BusinessAuthException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(BusinessAuthException e) {
        log.warn("error :", e);
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}