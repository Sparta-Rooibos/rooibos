package sparta.rooibos.hub.application.dto.hubManager.request;

import java.util.UUID;

public record CreateHubManagerRequest(
        UUID userId,
        UUID hubId
) {}
