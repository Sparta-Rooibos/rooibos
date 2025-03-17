package sparta.rooibos.hub.application.dto.request;

import java.util.UUID;

public record UpdateHubRequestDto(
        UUID hubId,
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {}
