package com.sparta.rooibos.stock.application.custom;

import com.sparta.rooibos.stock.application.exception.ErrorCodeProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCodeProvider {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COM-01", "Internal Server Error"),
    JSON_PARSING_ERROR(HttpStatus.BAD_REQUEST,"COM-02","JSON 파싱 에러"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND,"COM-03","Resource not found"),
    INVALID_CODE(HttpStatus.BAD_REQUEST,"COM-04","Invalid code"),
    SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COM-05","SQL 에러"),
    DOMAIN_VALIDATE_ERROR(HttpStatus.BAD_REQUEST,"COM-06","도메인 validation 에러");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
