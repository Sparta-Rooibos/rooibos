package com.sparta.rooibos.product.domain.feign.dto;

import java.time.LocalDateTime;

public record Client(String id,
                     String name,
                     String address,
                     ManageHub manageHub,
                     String type,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
}
