package com.sparta.rooibos.hub.application.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HubErrorCode {

    HUB_NOT_FOUND(HttpStatus.NO_CONTENT, "HUB-01", "해당 허브를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
