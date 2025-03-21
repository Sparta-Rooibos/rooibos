package sparta.rooibos.hub.application.dto.hubManager.response;

import sparta.rooibos.hub.domain.model.HubManager;

import java.util.UUID;

public record GetHubManagerHubIdResponse(
        UUID hubId
) {
    public static GetHubManagerHubIdResponse from(HubManager hubManager) {
        return new GetHubManagerHubIdResponse(hubManager.getHub().getHubId());
    }
}
