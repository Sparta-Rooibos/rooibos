package com.sparta.rooibus.order.domain.model;

public enum OrderStatus {
    PENDING,      // 주문 생성 후 승인 대기 중
    CONFIRMED,    // 주문 확인됨
    SHIPPED,      // 출고됨
    PROCESSING,   // 배송중
    DELIVERED,    // 배송 완료됨
    CANCELED,     // 주문 취소됨
    RETURNED, DENIED;     // 반품됨

    public static OrderStatus fromString(String value) {
        for (OrderStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}

