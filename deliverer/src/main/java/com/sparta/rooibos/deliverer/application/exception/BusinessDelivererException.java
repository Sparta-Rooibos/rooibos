package com.sparta.rooibos.deliverer.application.exception;

import com.sparta.rooibos.deliverer.application.exception.custom.DelivererErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessDelivererException extends RuntimeException {
    private final DelivererErrorCode errorCode;

    public BusinessDelivererException(DelivererErrorCode delivererErrorCode) {
        super(delivererErrorCode.getMessage());
        this.errorCode = delivererErrorCode;
    }

    public HttpStatus getStatus() { return errorCode.getStatus(); }
    public String getCode() { return errorCode.getCode(); }
    public String getErrorMessage() { return errorCode.getMessage(); }
}
