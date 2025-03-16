package sparta.rooibos.hub.presentation.dto;

import java.util.UUID;

public record UpdateRequestDto(
        UUID hubId,
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {}
