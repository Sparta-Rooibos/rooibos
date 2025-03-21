package com.sparta.rooibus.delivery.domain.repository;

import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibus.delivery.domain.model.Pagination;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

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
}
