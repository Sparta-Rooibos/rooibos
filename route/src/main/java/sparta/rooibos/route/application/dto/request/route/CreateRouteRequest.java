package sparta.rooibos.route.application.dto.request.route;

import java.util.UUID;

public record CreateRouteRequest(
    UUID fromHubId,
    UUID toHubId,
    Integer distance,
    Integer timeCost
) {}
