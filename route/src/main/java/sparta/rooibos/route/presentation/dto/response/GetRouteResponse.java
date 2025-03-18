package sparta.rooibos.route.presentation.dto.response;

import sparta.rooibos.route.domain.model.Route;

import java.util.UUID;

public record GetRouteResponse(
        UUID fromHubId,
        UUID toHubId,
        String distance,
        String timeCost
) {
    public static GetRouteResponse from(Route route) {
        return new GetRouteResponse(
                route.getFromHubId(),
                route.getToHubId(),
                route.getDistance(),
                route.getTimeCost()
        );
    }
}
