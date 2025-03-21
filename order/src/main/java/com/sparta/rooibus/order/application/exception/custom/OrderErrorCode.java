package com.sparta.rooibus.order.application.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NO_CONTENT, "ONF-01", "해당 주문을 찾을 수 없습니다."),
    PRODUCT_NOT_ENOUGH(HttpStatus.NO_CONTENT, "PNE-01", "해당 상품의 양이 없어 주문이 취소되었습니다."),
    FEIGN_DELIVERY_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "FDE-01", "배송 서비스 호출 중 오류가 발생했습니다."),
    FEIGN_STOCK_ERROR(HttpStatus.SERVICE_UNAVAILABLE,"FSE-01" ,"재고 서비스 호출 중 오류가 발생했습니다." ),
    FEIGN_HUB_ERROR(HttpStatus.SERVICE_UNAVAILABLE,"FHE-01" ,"허브 서비스 호출 중 오류가 발생했습니다." ),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST,"BRE-01" ,"요청이 잘못되었습니다." );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
