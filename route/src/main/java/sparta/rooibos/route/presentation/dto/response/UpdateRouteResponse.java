package sparta.rooibos.route.presentation.dto.response;

import sparta.rooibos.route.domain.model.Route;

import java.util.UUID;

public record UpdateRouteResponse(
        UUID fromHubId,
        UUID toHubId,
        String distance,
        String timeCost
) {
    public static UpdateRouteResponse from(Route route) {
        return new UpdateRouteResponse(
                route.getFromHubId(),
                route.getToHubId(),
                route.getDistance(),
                route.getTimeCost()
        );
    }
}
