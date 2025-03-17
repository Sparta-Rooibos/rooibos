package sparta.rooibos.hub.application.dto.response;

import sparta.rooibos.hub.domain.model.Hub;

import java.util.List;
import java.util.UUID;

public record SearchHubResponseDto(
        int page,
        int size,
        Long totalElements,
        Long totalPages,
        List<HubResponseDto> contents
) {
    public static SearchHubResponseDto of(int page, int size, Long totalElements, List<Hub> rawContents) {
        List<HubResponseDto> contents = rawContents.stream()
                .map(HubResponseDto::from)
                .toList();

        return new SearchHubResponseDto(
                page,
                size,
                totalElements,
                (totalElements + size - 1) / size,
                contents
        );
    }

    public record HubResponseDto(
            UUID hubId,
            String name,
            String region,
            String address,
            String latitude,
            String longitude
    ) {
        public static HubResponseDto from(Hub hub) {
            return new HubResponseDto(
                    hub.getHubId(),
                    hub.getName(),
                    hub.getRegion(),
                    hub.getAddress(),
                    hub.getLatitude(),
                    hub.getLongitude()
            );
        }
    }
}
