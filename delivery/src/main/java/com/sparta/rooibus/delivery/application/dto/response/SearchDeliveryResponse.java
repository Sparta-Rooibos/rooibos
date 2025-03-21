package com.sparta.rooibus.delivery.application.dto.response;

import com.sparta.rooibus.delivery.domain.entity.Delivery;
import com.sparta.rooibus.delivery.domain.model.Pagination;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SearchDeliveryResponse(
    int page,
    int size,
    Long totalElements,
    Long totalPages,
    List<SearchDelivery> contents
) {
    public static SearchDeliveryResponse of(int page, int size, Long totalElements, List<Delivery> rawContents) {
        List<SearchDelivery> contents = rawContents.stream()
            .map(SearchDelivery::from)
            .toList();

        return new SearchDeliveryResponse(
            page,
            size,
            totalElements,
            (totalElements + size - 1) / size,
            contents
        );
    }

    public static SearchDeliveryResponse from(Pagination<Delivery> deliveryPagination) {
        List<SearchDelivery> contents = deliveryPagination.getContent().stream()
            .map(SearchDelivery::from)
            .toList();

        return new SearchDeliveryResponse(
            deliveryPagination.getPage(),
            deliveryPagination.getSize(),
            deliveryPagination.getTotal(),
            (deliveryPagination.getTotal() + deliveryPagination.getSize() - 1) / deliveryPagination.getSize(),
            contents
        );
    }

    public record SearchDelivery(
        UUID deliveryId,
        String status,
        UUID departure,
        UUID arrival,
        String address,
        UUID recipient,
        String slackAccount,
        UUID clientDeliver,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        public static SearchDelivery from(Delivery delivery){
            return new SearchDelivery(
                delivery.getId(),
                delivery.getStatus().toString(),
                delivery.getDeparture(),
                delivery.getArrival(),
                delivery.getAddress(),
                delivery.getRecipient(),
                delivery.getSlackAccount(),
                delivery.getClientDeliver(),
                delivery.getCreatedAt(),
                delivery.getUpdatedAt()
            );
        }
    }
}
