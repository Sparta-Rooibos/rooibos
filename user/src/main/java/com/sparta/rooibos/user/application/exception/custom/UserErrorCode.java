package com.sparta.rooibos.user.application.exception.custom;

import com.sparta.rooibos.user.application.exception.ErrorCodeProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCodeProvider {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USR-01", "해당 사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USR-02", "이미 존재하는 이메일입니다."),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "USR-03", "유효하지 않은 역할입니다."),
    INVALID_SLACK_ACCOUNT(HttpStatus.BAD_REQUEST, "USR-04", "슬랙 계정이 올바르지 않습니다."),
    FORBIDDEN_USER_ACCESS(HttpStatus.FORBIDDEN, "USR-05", "해당 사용자에 대한 권한이 없습니다."),
    INVALID_UPDATE_REQUEST(HttpStatus.BAD_REQUEST, "USR-06", "업데이트 요청이 올바르지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
