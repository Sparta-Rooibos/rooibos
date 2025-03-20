package com.sparta.rooibus.order.application.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NO_CONTENT, "ONF-01", "해당 주문을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
