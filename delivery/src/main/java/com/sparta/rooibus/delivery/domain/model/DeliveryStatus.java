package com.sparta.rooibus.delivery.domain.model;

public enum DeliveryStatus {
        PENDING,       // 허브 대기중
        HUB_MOVING,        // 허브 에서 허브로 이동중
        ARRIVAL_HUB_ARRIVED,  // 목적지 허브 도착
        DELIVERING,        // 배송중
        DELIVERED          // 배송 완료
}


