package com.sparta.rooibus.order.domain.model;

public enum OrderStatus {
    PENDING("주문 접수"),
    PROCESSING("주문 처리중"),
    DELIVERING("배송 중"),
    DELIVERED("배송 완료"),
    CANCELLED("취소");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
