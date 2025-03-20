package sparta.rooibos.hub.application.dto.response;

import sparta.rooibos.hub.domain.model.Hub;

import java.util.UUID;

public record CreateHubResponse(
        UUID hubId,
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {
    public static CreateHubResponse from(Hub hub) {
        return new CreateHubResponse(
                hub.getHubId(),
                hub.getName(),
                hub.getRegion(),
                hub.getAddress(),
                hub.getLatitude(),
                hub.getLongitude()
        );
    }
}
