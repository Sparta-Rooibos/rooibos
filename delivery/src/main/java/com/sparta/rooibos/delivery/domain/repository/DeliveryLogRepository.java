package com.sparta.rooibos.delivery.domain.repository;

import com.sparta.rooibos.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibos.delivery.domain.model.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryLogRepository {
    Pagination<DeliveryLog> searchOrders(String keyword,
        String filterKey,
        String filterValue,
        String sort,
        int page,
        int size);

    DeliveryLog save(DeliveryLog deliveryLogId);

    Optional<DeliveryLog> findById(UUID deliveryId);

    Pagination<DeliveryLog> searchDeliveryLogs(String keyword, String filterKey, String filterValue, String sort, int page, int size);

    List<DeliveryLog> findAllByDeliveryId(UUID deliveryId);

    List<DeliveryLog> saveAll(List<DeliveryLog> deliveryLogs);
}
