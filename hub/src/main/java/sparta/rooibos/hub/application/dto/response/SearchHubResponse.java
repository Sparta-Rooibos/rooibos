package sparta.rooibos.hub.application.dto.response;

import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.model.Pagination;

import java.util.List;
import java.util.UUID;

public record SearchHubResponse(
        int page,
        int size,
        Long totalElements,
        Long totalPages,
        List<HubResponseDto> contents
) {
    public static SearchHubResponse from(Pagination<Hub> pagination) {
        int size = pagination.getSize();

        return new SearchHubResponse(
                pagination.getPage(),
                size,
                pagination.getTotal(),
                (pagination.getTotal() + size - 1) / size,
                pagination.getContent().stream()
                        .map(HubResponseDto::from)
                        .toList()
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
