package com.sparta.rooibos.hub.application.dto.hubManager.response;

import com.sparta.rooibos.hub.domain.model.HubManager;

import java.util.UUID;

public record CreateHubManagerResponse(
        UUID hubManagerId,
        UUID userId,
        UUID hubId
) {
    public static CreateHubManagerResponse from(HubManager hubManager) {
        return new CreateHubManagerResponse(
                hubManager.getHubManagerId(),
                hubManager.getUserId(),
                hubManager.getBelongingHubId()
        );
    }
}
