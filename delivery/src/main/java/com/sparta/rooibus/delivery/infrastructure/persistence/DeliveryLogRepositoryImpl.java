package com.sparta.rooibus.delivery.infrastructure.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibus.delivery.domain.model.Pagination;
import com.sparta.rooibus.delivery.domain.repository.DeliveryLogRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeliveryLogRepositoryImpl implements DeliveryLogRepository {

    private final JpaDeliveryLogRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Pagination<DeliveryLog> searchOrders(String keyword, String filterKey,
        String filterValue, String sort, int page, int size) {
        return null;
    }

    @Override
    public DeliveryLog save(DeliveryLog deliveryLogId) {
        return jpaRepository.save(deliveryLogId);
    }

    @Override
    public Optional<DeliveryLog> findById(UUID deliveryId) {
        return jpaRepository.findById(deliveryId);
    }
}
