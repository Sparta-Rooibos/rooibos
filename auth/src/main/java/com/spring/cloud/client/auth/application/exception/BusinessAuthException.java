package com.spring.cloud.client.auth.application.exception;

import com.spring.cloud.client.auth.application.exception.custom.AuthErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessAuthException extends RuntimeException {
    private final AuthErrorCode errorCode;

    public BusinessAuthException(AuthErrorCode authErrorCode) {
        super(authErrorCode.getMessage());
        this.errorCode = authErrorCode;
    }

    public HttpStatus getStatus() { return errorCode.getStatus(); }
    public String getCode() { return errorCode.getCode(); }
    public String getErrorMessage() { return errorCode.getMessage(); }
}
