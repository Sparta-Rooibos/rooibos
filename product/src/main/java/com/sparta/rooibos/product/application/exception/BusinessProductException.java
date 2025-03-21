package com.sparta.rooibos.product.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class BusinessProductException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus status;
    public BusinessProductException(ProductErrorCode productErrorCode) {
        super(productErrorCode.getMessage());
        this.errorCode = productErrorCode.getCode();
        this.status = productErrorCode.getStatus();
    }
}
