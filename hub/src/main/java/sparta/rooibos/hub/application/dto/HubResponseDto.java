package sparta.rooibos.hub.application.dto;

import java.util.UUID;

public record HubResponseDto(
        UUID hubId,
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {}
