package com.sparta.rooibos.client.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ClientErrorCode {

    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COM-01", "Internal Server Error"),
    JSON_PARSING_ERROR(HttpStatus.BAD_REQUEST,"COM-02","JSON 파싱 에러"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND,"COM-03","Resource not found"),
    INVALID_CODE(HttpStatus.BAD_REQUEST,"COM-04","Invalid code"),
    SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COM-05","SQL 에러"),

    // 클라이언트(업체)
    NOT_FOUND_CLIENT(HttpStatus.NOT_FOUND, "CLI-01", "이미 존재하는 업체입니다."),
    NOT_EXITS_CLIENT(HttpStatus.BAD_REQUEST, "CLI-02", "해당 하는 업체가 존재하지 않습니다."),
    NOT_EXITS_CLIENT_MANAGER(HttpStatus.NOT_FOUND, "CLI-03", "업체 담당자가 이미 존재합니다."),
    NOT_FOUND_HUB(HttpStatus.NOT_FOUND, "CLI-04", "존재하지 않는 허브입니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
