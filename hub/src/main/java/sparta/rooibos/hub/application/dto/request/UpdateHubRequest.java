package sparta.rooibos.hub.application.dto.request;

public record UpdateHubRequest(
        String name,
        String region,
        String address,
        String latitude,
        String longitude
) {}
