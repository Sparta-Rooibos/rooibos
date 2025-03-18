package com.sparta.rooibos.client.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessClientException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus status;

    public BusinessClientException(ClientErrorCode clientErrorCode) {
        super(clientErrorCode.getMessage());
        this.errorCode = clientErrorCode.getCode();
        this.status = clientErrorCode.getStatus();
    }
}
