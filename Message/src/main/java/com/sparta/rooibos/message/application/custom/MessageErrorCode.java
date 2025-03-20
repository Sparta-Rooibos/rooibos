package com.sparta.rooibos.message.application.custom;

import com.sparta.rooibos.message.application.exception.ErrorCodeProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MessageErrorCode implements ErrorCodeProvider {
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "MES-01", "슬랙 메시지가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
