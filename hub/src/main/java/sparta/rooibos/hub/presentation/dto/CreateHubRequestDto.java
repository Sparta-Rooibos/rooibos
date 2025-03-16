package sparta.rooibos.hub.presentation.dto;

public record CreateHubRequestDto(
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {}
