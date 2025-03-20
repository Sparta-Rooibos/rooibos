package com.sparta.rooibus.order.application.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NO_CONTENT, "ONF-01", "해당 주문을 찾을 수 없습니다."),
    PRODUCT_NOT_ENOUGH(HttpStatus.NO_CONTENT, "PNE-01", "해당 상품의 양이 없어 주문이 취소되었습니다."),
    FEIGN_WORK_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "FWE-01", "외부 서비스 호출 중 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
