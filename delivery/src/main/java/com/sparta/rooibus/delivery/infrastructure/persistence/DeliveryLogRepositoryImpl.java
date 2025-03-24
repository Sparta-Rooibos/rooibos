package com.sparta.rooibus.delivery.infrastructure.persistence;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibus.delivery.domain.entity.Delivery;
import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibus.delivery.domain.entity.QDeliveryLog;
import com.sparta.rooibus.delivery.domain.model.Pagination;
import com.sparta.rooibus.delivery.domain.repository.DeliveryLogRepository;
import java.util.List;
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

    @Override
    public Pagination<DeliveryLog> searchDeliveryLogs(String keyword, String filterKey,
        String filterValue, String sort, int page, int size) {
        BooleanBuilder builder = new BooleanBuilder();
        QDeliveryLog deliveryLog = QDeliveryLog.deliveryLog;

        builder.and(deliveryLog.deletedAt.isNull());

        if (!keyword.isEmpty()) {
            builder.and(deliveryLog.sequence.containsIgnoreCase(keyword));
        }

        if ("arrival".equalsIgnoreCase(filterKey) && !filterValue.isEmpty()) {
            builder.and(deliveryLog.arrival.eq(UUID.fromString(filterValue)));
        }
        OrderSpecifier<?> sortDelivery = sort.equalsIgnoreCase("desc")
            ? deliveryLog.createdAt.desc()
            : deliveryLog.createdAt.asc();

        List<DeliveryLog> deliveryLogs = queryFactory
            .select(deliveryLog)
            .from(deliveryLog)
            .where(builder)
            .orderBy(sortDelivery,deliveryLog.updatedAt.desc())
            .offset((long) page * size)
            .limit(size)
            .fetch();

        Long total = queryFactory
            .select(deliveryLog.count())
            .from(deliveryLog)
            .where(builder)
            .fetchOne();

        return Pagination.of(page,size,total,deliveryLogs);
    }

    @Override
    public List<DeliveryLog> findAllByDeliveryId(UUID deliveryId) {
        return jpaRepository.findAllByDeliveryIdAndDeletedAtIsNull(deliveryId);
    }

    @Override
    public List<DeliveryLog> saveAll(List<DeliveryLog> deliveryLogs) {
        return jpaRepository.saveAll(deliveryLogs);
    }
}
