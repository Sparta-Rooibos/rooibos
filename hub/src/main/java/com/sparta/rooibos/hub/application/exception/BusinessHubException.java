package com.sparta.rooibos.hub.application.exception;

import com.sparta.rooibos.hub.application.exception.custom.HubErrorCode;
import org.springframework.http.HttpStatus;

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
