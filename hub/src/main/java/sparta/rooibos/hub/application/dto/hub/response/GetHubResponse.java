package sparta.rooibos.hub.application.dto.hub.response;

import sparta.rooibos.hub.domain.model.Hub;

import java.util.UUID;

// TODO merge 하면 자꾸 사라진다...!!
public record GetHubResponse(
        UUID hubId,
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {
    public static GetHubResponse from(Hub hub) {
        return new GetHubResponse(
                hub.getHubId(),
                hub.getName(),
                hub.getRegion(),
                hub.getAddress(),
                hub.getLatitude(),
                hub.getLongitude()
        );
    }
}
