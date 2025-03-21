package com.sparta.rooibos.message.application.exception;

import com.sparta.rooibos.message.application.custom.MessageErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessMessageException extends RuntimeException {
    private final ErrorCodeProvider errorCode;

    public BusinessMessageException(MessageErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return errorCode.getStatus();
    }

    public String getCode() {
        return errorCode.getCode();
    }

    public String getErrorMessage() {
        return errorCode.getMessage();
    }

}
