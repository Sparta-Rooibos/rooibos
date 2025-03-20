package sparta.rooibos.hub.application.dto.hubManager.request;

import sparta.rooibos.hub.domain.model.Hub;

import java.util.UUID;

public record CreateHubManagerRequest(
        UUID userId,
        UUID hubId
) {}
