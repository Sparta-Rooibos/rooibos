package sparta.rooibos.hub.application.dto;

import java.util.UUID;

public record CreateHubResponseDto(
        UUID hubId,
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {}
