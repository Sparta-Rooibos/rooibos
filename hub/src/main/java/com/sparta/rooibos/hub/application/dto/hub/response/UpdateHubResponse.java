package com.sparta.rooibos.hub.application.dto.hub.response;

import com.sparta.rooibos.hub.domain.model.Hub;

import java.util.UUID;

public record UpdateHubResponse(
        UUID hubId,
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {
    public static UpdateHubResponse from(Hub hub) {
        return new UpdateHubResponse(
                hub.getHubId(),
                hub.getName(),
                hub.getRegion(),
                hub.getAddress(),
                hub.getLatitude(),
                hub.getLongitude()
        );
    }
}
