package sparta.rooibos.route.presentation.dto.response;

import sparta.rooibos.route.domain.model.Route;

import java.util.UUID;

public record CreateRouteResponse(
        UUID fromHubId,
        UUID toHubId,
        Integer distance,
        Integer timeCost
) {
    public static CreateRouteResponse from(Route route) {
        return new CreateRouteResponse(
                route.getFromHubId(),
                route.getToHubId(),
                route.getDistance(),
                route.getTimeCost()
        );
    }
}
