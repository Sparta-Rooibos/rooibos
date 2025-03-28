package com.sparta.rooibos.delivery.domain.repository;

import com.sparta.rooibos.delivery.domain.entity.Delivery;
import com.sparta.rooibos.delivery.domain.model.Pagination;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRepository {

    Pagination<Delivery> searchDeliveries(String keyword,
        String filterKey,
        String filterValue,
        String sort,
        int page,
        int size);

    Delivery save(Delivery order);

    Optional<Delivery> findById(UUID deliveryId);

    Delivery findByIdAndHub(UUID deliveryId, UUID hubId);

    Delivery findByDeliver(UUID userId,UUID deliveryId);

    Pagination<Delivery> searchDeliveriesByDeliver(UUID deliverId, String keyword, String filterKey,
        String filterValue, String sort, int page, int size);

    Pagination<Delivery> findAllByDeparture(UUID hubId, String keyword, String filterKey,
        String filterValue, String sort, int page, int size);
}
