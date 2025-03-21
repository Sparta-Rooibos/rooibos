package com.sparta.rooibus.delivery.infrastructure.persistence;

import static com.sparta.rooibus.delivery.domain.entity.QDelivery.delivery;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibus.delivery.application.dto.request.SearchDeliveryRequestDTO;
import com.sparta.rooibus.delivery.domain.entity.Delivery;
import com.sparta.rooibus.delivery.domain.entity.QDelivery;
import com.sparta.rooibus.delivery.domain.model.DeliveryStatus;
import com.sparta.rooibus.delivery.domain.model.Pagination;
import com.sparta.rooibus.delivery.domain.repository.DeliveryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final JPAQueryFactory queryFactory;
    private final JpaDeliveryRepository jpaRepository;

    @Override
    public Pagination<Delivery> searchOrders(String keyword, String filterKey, String filterValue,
        String sort, int page, int size) {
        BooleanBuilder builder = new BooleanBuilder();
        QDelivery delivery = QDelivery.delivery;

        builder.and(delivery.deletedAt.isNull());

        if (!keyword.isEmpty()) {
            builder.and(delivery.slackAccount.containsIgnoreCase(keyword));
        }

        if ("arrival".equalsIgnoreCase(filterKey) && !filterValue.isEmpty()) {
            builder.and(delivery.arrival.eq(UUID.fromString(filterValue)));
        }

        OrderSpecifier<?> sortDelivery = sort.equalsIgnoreCase("desc")
            ? delivery.createdAt.desc()
            : delivery.createdAt.asc();

        List<Delivery> deliveries = queryFactory
            .select(delivery)
            .from(delivery)
            .where(builder)
            .orderBy(sortDelivery,delivery.updatedAt.desc())
            .offset((long) page * size)
            .limit(size)
            .fetch();

        Long total = queryFactory
            .select(delivery.count())
            .from(delivery)
            .where(builder)
            .fetchOne();


        return Pagination.of(page,size,total,deliveries);
    }

    @Override
    public Delivery save(Delivery delivery) {
        return jpaRepository.save(delivery);
    }

    @Override
    public Optional<Delivery> findById(UUID deliveryId) {
        return jpaRepository.findById(deliveryId);
    }
}
