package com.sparta.rooibus.delivery.application.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryError {
    DELIVERY_NOT_FOUND(HttpStatus.NO_CONTENT, "DNF-01", "해당 배송을 찾을 수 없습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
