package com.sparta.rooibos.stock.application.exception;

import com.sparta.rooibos.stock.application.custom.StockErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessStockException extends RuntimeException {
    private final ErrorCodeProvider errorCode;

    public BusinessStockException(StockErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() { return errorCode.getStatus(); }
    public String getCode() { return errorCode.getCode(); }
    public String getErrorMessage() { return errorCode.getMessage(); }
}
