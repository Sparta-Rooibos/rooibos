package sparta.rooibos.route.application.dto.request.route;

import java.util.UUID;

public record UpdateRouteRequest(
        UUID fromHubId,
        UUID toHubId,
        Integer distance,
        Integer timeCost
) {}
