package com.sparta.rooibos.stock.application.custom;

import com.sparta.rooibos.stock.application.exception.ErrorCodeProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StockErrorCode implements ErrorCodeProvider {
    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND,"STK-01", "존재하지 않는 stock입니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
