package com.sparta.rooibos.hub.application.dto.response;

import java.util.UUID;

public record UpdateHubResponseDto(
        UUID hubId,
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {}
