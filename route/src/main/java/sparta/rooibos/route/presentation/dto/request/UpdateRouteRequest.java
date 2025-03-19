package sparta.rooibos.route.presentation.dto.request;

import java.util.UUID;

public record UpdateRouteRequest(
        UUID fromHubId,
        UUID toHubId,
        Integer distance,
        Integer timeCost
) {}
