package com.sparta.rooibos.client.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ClientErrorCode {
    NOT_FOUND_CLIENT(HttpStatus.NOT_FOUND, "CLI-01", "이미 존재하는 업체입니다."),
    NOT_EXITS_CLIENT(HttpStatus.BAD_REQUEST, "CLI-02", "해당 하는 업체가 존재하지 않습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
