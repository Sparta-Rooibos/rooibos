package com.sparta.rooibos.hub.application.exception;

import org.springframework.http.HttpStatus;
import com.sparta.rooibos.hub.application.exception.custom.HubManagerErrorCode;

public class BusinessHubManagerException extends RuntimeException {
    private final HubManagerErrorCode hubManagerErrorCode;

    public BusinessHubManagerException(HubManagerErrorCode hubManagerErrorCode) {
        super(hubManagerErrorCode.getMessage());
        this.hubManagerErrorCode = hubManagerErrorCode;
    }

    public HttpStatus getStatus() {
        return hubManagerErrorCode.getStatus();
    }

    public String getErrorCode() {
        return hubManagerErrorCode.getCode();
    }

    public String getMessage() {
        return hubManagerErrorCode.getMessage();
    }
}
