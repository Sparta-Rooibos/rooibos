package com.sparta.rooibos.route.application.dto.request.route;

import java.util.UUID;

public record CreateRouteRequest(
    UUID fromHubId,
    UUID toHubId
) {}
