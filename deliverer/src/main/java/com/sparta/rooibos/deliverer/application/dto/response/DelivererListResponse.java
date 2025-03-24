package com.sparta.rooibos.deliverer.application.dto.response;

import com.sparta.rooibos.deliverer.domain.entity.Deliverer;
import com.sparta.rooibos.deliverer.domain.entity.DelivererType;
import com.sparta.rooibos.deliverer.domain.model.Pagination;

import java.util.List;
import java.util.UUID;

public record DelivererListResponse(
        int page,
        int size,
        long totalElements,
        long totalPages,
        List<DelivererSummary> contents
) {
    public static DelivererListResponse from(Pagination<Deliverer> pagination) {
        int size = pagination.getSize();
        return new DelivererListResponse(
                pagination.getPage(),
                size,
                pagination.getTotal(),
                (pagination.getTotal() + size - 1) / size,
                pagination.getContent().stream().map(DelivererSummary::from).toList()
        );
    }

    public record DelivererSummary(
            UUID id,
            String username,
            String email,
            String phone,
            String slackAccount,
            UUID hubId,
            DelivererType type,
            int order
    ) {
        public static DelivererSummary from(Deliverer deliverer) {
            return new DelivererSummary(
                    deliverer.getId(),
                    deliverer.getUsername(),
                    deliverer.getEmail(),
                    deliverer.getPhone(),
                    deliverer.getSlackAccount(),
                    deliverer.getHubId(),
                    deliverer.getType(),
                    deliverer.getOrder()
            );
        }
    }
}

