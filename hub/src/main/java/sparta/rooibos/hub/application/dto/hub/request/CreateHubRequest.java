package sparta.rooibos.hub.application.dto.hub.request;

public record CreateHubRequest(
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {}
