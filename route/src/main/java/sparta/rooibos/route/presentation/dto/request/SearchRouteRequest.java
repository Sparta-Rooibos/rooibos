package sparta.rooibos.route.presentation.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record SearchRouteRequest(
        UUID fromHubId,
        UUID toHubId,
        String sort, // default, distance, timeCost
        int size,
        LocalDateTime lastCreatedAt,
        String lastDistance,
        String lastTotalCost
) {}
