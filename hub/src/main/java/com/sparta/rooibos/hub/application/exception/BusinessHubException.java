package com.sparta.rooibos.hub.application.exception;

import org.springframework.http.HttpStatus;
import com.sparta.rooibos.hub.application.exception.custom.HubErrorCode;

public class BusinessHubException extends RuntimeException {
    private final HubErrorCode hubErrorCode;

    public BusinessHubException(HubErrorCode hubErrorCode) {
        super(hubErrorCode.getMessage());
        this.hubErrorCode = hubErrorCode;
    }

    public HttpStatus getStatus() {
        return hubErrorCode.getStatus();
    }

    public String getErrorCode() {
        return hubErrorCode.getCode();
    }

    public String getMessage() {
        return hubErrorCode.getMessage();
    }
}
