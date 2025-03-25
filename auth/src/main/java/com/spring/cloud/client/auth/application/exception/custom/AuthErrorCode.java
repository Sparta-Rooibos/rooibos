package com.spring.cloud.client.auth.application.exception.custom;

import com.spring.cloud.client.auth.application.exception.ErrorCodeProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCodeProvider {

    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUT-01", "유효한 계정이 아닙니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AUT-02", "비밀번호가 일치하지 않습니다."),
    BLOCKED_ACCOUNT(HttpStatus.FORBIDDEN, "AUT-03", "해당 계정은 보안 조치로 로그인할 수 없습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "AUT-04", "유효하지 않은 Refresh Token입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUT-05", "리프레시 토큰이 만료되었습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "AUT-06", "로그인이 필요합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
