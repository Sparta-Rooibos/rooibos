package sparta.rooibos.hub.application.dto.request;

public record CreateHubRequestDto(
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {}
