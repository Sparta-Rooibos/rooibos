package com.sparta.rooibos.delivery.infrastructure.persistence;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibos.delivery.domain.entity.Delivery;
import com.sparta.rooibos.delivery.domain.entity.QDelivery;
import com.sparta.rooibos.delivery.domain.model.Pagination;
import com.sparta.rooibos.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final JPAQueryFactory queryFactory;
    private final JpaDeliveryRepository jpaRepository;

    @Override
    public Pagination<Delivery> searchDeliveries(String keyword, String filterKey, String filterValue,
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

    @Override
    public Delivery findByIdAndHub(UUID deliveryId, UUID hubId) {
        return jpaRepository.findByDepartureAndIdAndDeletedAtIsNull(hubId,deliveryId).orElseThrow(
            ()-> new IllegalArgumentException("찾는 배송이 없음")
        );
    }

    @Override
    public Delivery findByDeliver(UUID userId,UUID deliveryId) {
        return jpaRepository.findByClientDeliverAndIdAndDeletedAtIsNull(userId,deliveryId).orElseThrow(
            ()-> new IllegalArgumentException("찾는 배송 정보 없음")
        );
    }

    @Override
    public Pagination<Delivery> searchDeliveriesByDeliver(UUID deliverId, String keyword,
        String filterKey, String filterValue, String sort, int page, int size) {
        BooleanBuilder builder = new BooleanBuilder();
        QDelivery delivery = QDelivery.delivery;

        builder.and(delivery.deletedAt.isNull());
        builder.and(delivery.clientDeliver.eq(deliverId));

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
    public Pagination<Delivery> findAllByDeparture(UUID hubId, String keyword, String filterKey,
        String filterValue, String sort, int page, int size) {
        BooleanBuilder builder = new BooleanBuilder();
        QDelivery delivery = QDelivery.delivery;

        builder.and(delivery.deletedAt.isNull());
        builder.and(delivery.departure.eq(hubId));

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


}
