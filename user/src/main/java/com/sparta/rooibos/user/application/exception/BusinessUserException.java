package com.sparta.rooibos.user.application.exception;

import com.sparta.rooibos.user.application.exception.custom.UserErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessUserException extends RuntimeException {
    private final UserErrorCode errorCode;

    public BusinessUserException(UserErrorCode userErrorCode) {
        super(userErrorCode.getMessage());
        this.errorCode = userErrorCode;
    }

    public HttpStatus getStatus() { return errorCode.getStatus(); }
    public String getCode() { return errorCode.getCode(); }
    public String getErrorMessage() { return errorCode.getMessage(); }
}
