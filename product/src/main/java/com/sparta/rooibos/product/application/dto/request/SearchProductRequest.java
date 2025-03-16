package com.sparta.rooibos.product.application.dto.request;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public record SearchProductRequest(UUID id, String name, Boolean isDeleted, Pageable pageable) {
}
