package com.sparta.rooibus.delivery.domain.model;

public enum DeliveryStatus {
    PENDING("대기중"),  // 배송 대기 중
    IN_PROGRESS("배송 중"),  // 배송 진행 중
    COMPLETED("배송 완료"),  // 배송 완료
    CANCELED("배송 취소"),  // 배송 취소
    FAILED("배송 실패");  // 배송 실패

    private final String description;

    // 생성자
    DeliveryStatus(String description) {
        this.description = description;
    }

    // 상태 설명을 반환하는 메서드
    public String getDescription() {
        return description;
    }
}

