package sparta.rooibos.route.presentation.dto.request;

import java.util.UUID;

public record CreateRouteRequest(
    UUID fromHubId,
    UUID toHubId,
    Integer distance,
    Integer timeCost
) {}
