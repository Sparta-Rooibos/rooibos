package sparta.rooibos.hub.application.dto.request;

public record SearchHubRequest(
        String name,
        String region,
        int page,
        int size
) {}
