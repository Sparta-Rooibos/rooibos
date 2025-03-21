package com.sparta.rooibos.stock.presentation.controller;

import com.sparta.rooibos.stock.application.custom.CommonErrorCode;
import com.sparta.rooibos.stock.application.exception.BusinessStockException;
import com.sparta.rooibos.stock.presentation.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 그외 exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("error :", e);
        return ErrorResponse.toResponseEntity(e);
    }

    // 공통 에러처리
    // json이 틀렸을 경우
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("warm :", e);
        return ErrorResponse.toResponseEntity(CommonErrorCode.JSON_PARSING_ERROR);
    }

    // 리소스가 존재하지 않는경우
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoResourceFoundException e) {
        log.warn("warn :", e);
        return ErrorResponse.toResponseEntity(CommonErrorCode.RESOURCE_NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("warn :", e);
        return ErrorResponse.toResponseEntity(CommonErrorCode.INVALID_CODE);
    }

    // 도메인에서 에러를 던졌을때
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> domainErrorException(IllegalArgumentException e) {
        log.warn("warn :", e);
        return ErrorResponse.toResponseEntity(e);
    }


    // 비즈니스 예외
    @ExceptionHandler(BusinessStockException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(BusinessStockException e) {
        log.error("business error :", e);
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
