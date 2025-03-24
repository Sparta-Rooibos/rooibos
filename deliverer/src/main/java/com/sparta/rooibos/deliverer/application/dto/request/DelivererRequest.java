package com.sparta.rooibos.deliverer.application.dto.request;

import com.sparta.rooibos.deliverer.domain.entity.DelivererType;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record DelivererRequest(
        @NotBlank UUID userId,
        @NotBlank UUID hubId,
        @NotBlank DelivererType type
) {}