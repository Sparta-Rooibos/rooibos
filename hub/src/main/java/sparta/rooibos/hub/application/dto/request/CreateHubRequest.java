package sparta.rooibos.hub.application.dto.request;

public record CreateHubRequest(
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {}
