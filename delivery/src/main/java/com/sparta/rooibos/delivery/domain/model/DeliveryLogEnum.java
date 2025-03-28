package com.sparta.rooibos.delivery.domain.model;

public enum DeliveryLogEnum {
    PENDING,            // 허브 대기중
    DELIVERING,         // 허브 에서 허브로 이동중
    DELIVERED,          // 목적지 허브에 도착
    CANCELED            // 배송이 취소되었을때
}
