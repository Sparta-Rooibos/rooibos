package com.sparta.rooibus.order.application.dto.response;

import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.model.Pagination;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SearchOrderResponse(
    int page,
    int size,
    Long totalElements,
    Long totalPages,
    List<SearchOrder> contents
) {
    public static SearchOrderResponse of(int page, int size, Long totalElements, List<Order> rawContents) {
        List<SearchOrder> contents = rawContents.stream()
            .map(SearchOrder::from)
            .toList();

        return new SearchOrderResponse(
            page,
            size,
            totalElements,
            (totalElements + size - 1) / size,
            contents
        );
    }

    public static SearchOrderResponse from(Pagination<Order> orderPagination) {
        List<SearchOrder> contents = orderPagination.getContent().stream()
            .map(SearchOrder::from)
            .toList();

        return new SearchOrderResponse(
            orderPagination.getPage(),
            orderPagination.getSize(),
            orderPagination.getTotal(),
            (orderPagination.getTotal() + orderPagination.getSize() - 1) / orderPagination.getSize(),
            contents
        );
    }

    public record SearchOrder(
        UUID id,
        UUID requestClientId,
        UUID responseClientId,
        UUID productId,
        int quantity,
        String requirement,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        public static SearchOrder from(Order order){
            return new SearchOrder(
                order.getId(),
                order.getRequestClientId(),
                order.getReceiveClientId(),
                order.getProductId(),
                order.getQuantity(),
                order.getRequirement(),
                order.getCreatedAt(),
                order.getUpdatedAt()
            );
        }
    }
}
