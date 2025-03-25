package com.sparta.rooibus.order.application.exception;

import com.sparta.rooibus.order.application.exception.custom.OrderErrorCode;
import org.springframework.http.HttpStatus;

public class BusinessOrderException extends RuntimeException {
    private final OrderErrorCode orderErrorCode;

    public BusinessOrderException(OrderErrorCode orderErrorCode) {
        super(orderErrorCode.getMessage());
        this.orderErrorCode = orderErrorCode;
    }

    public HttpStatus getStatus() {
        return orderErrorCode.getStatus();
    }

    public String getErrorCode() {
        return orderErrorCode.getCode();
    }

    public String getMessage() {
        return orderErrorCode.getMessage();
    }
}
