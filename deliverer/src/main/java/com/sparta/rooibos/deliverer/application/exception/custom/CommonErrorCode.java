package com.sparta.rooibos.deliverer.application.exception.custom;

import com.sparta.rooibos.deliverer.application.exception.ErrorCodeProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCodeProvider {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COM-01", "Internal Server Error"),
    JSON_PARSING_ERROR(HttpStatus.BAD_REQUEST,"COM-02","JSON 파싱 에러"),
    SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COM-05","SQL 에러");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
