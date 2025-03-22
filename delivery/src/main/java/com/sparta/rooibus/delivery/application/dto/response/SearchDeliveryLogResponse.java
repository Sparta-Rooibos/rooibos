package com.sparta.rooibus.delivery.application.dto.response;

import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibus.delivery.domain.model.Pagination;
import java.util.List;
import java.util.UUID;

public record SearchDeliveryLogResponse(
    int page,
    int size,
    Long totalElements,
    Long totalPages,
    List<SearchDeliveryLog> contents
) {
    public static SearchDeliveryLogResponse of(int page, int size, Long totalElements, List<DeliveryLog> rawContents) {
        List<SearchDeliveryLog> contents = rawContents.stream()
            .map(SearchDeliveryLog::from)
            .toList();

        return new SearchDeliveryLogResponse(
            page,
            size,
            totalElements,
            (totalElements + size - 1) / size,
            contents
        );
    }

    public static SearchDeliveryLogResponse from(Pagination<DeliveryLog> deliveryLogPagination) {
        List<SearchDeliveryLog> contents = deliveryLogPagination.getContent().stream()
            .map(SearchDeliveryLog::from)
            .toList();

        return new SearchDeliveryLogResponse(
            deliveryLogPagination.getPage(),
            deliveryLogPagination.getSize(),
            deliveryLogPagination.getTotal(),
            (deliveryLogPagination.getTotal() + deliveryLogPagination.getSize() - 1) / deliveryLogPagination.getSize(),
            contents
        );
    }

    public record SearchDeliveryLog(
        UUID id,
        UUID deliveryId,
        UUID departure,
        UUID arrival,
        String sequence,
        String expectedDistance,
        String expectedTime,
        String takenDistance,
        String takenTime,
        String status,
        UUID deliver
    ) {
        public static SearchDeliveryLog from(DeliveryLog deliveryLog){
            return new SearchDeliveryLog(
                deliveryLog.getId(),
                deliveryLog.getDeliveryId(),
                deliveryLog.getDeparture(),
                deliveryLog.getArrival(),
                deliveryLog.getSequence(),
                deliveryLog.getExpectedDistance(),
                deliveryLog.getExpectedTime(),
                deliveryLog.getTakenDistance(),
                deliveryLog.getTakenTime(),
                deliveryLog.getStatus().toString(),
                deliveryLog.getDeliver()
            );
        }
    }
}
