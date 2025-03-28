package com.sparta.rooibos.order.infrastructure.persistence;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibos.order.domain.entity.Order;
import com.sparta.rooibos.order.domain.entity.QOrder;
import com.sparta.rooibos.order.domain.model.Pagination;
import com.sparta.rooibos.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final JPAQueryFactory queryFactory;
    private final JpaOrderRepository jpaRepository;

    @Override
    public Order save(Order order) {
        return jpaRepository.save(order);
    }

    @Override
    public Pagination<Order> searchOrders(String keyword, String filterKey, String filterValue,
        String sort, int page, int size) {

        BooleanBuilder builder = new BooleanBuilder();
        QOrder order = QOrder.order;

//      삭제된 데이터는 검색 내용에서 제외
        builder.and(order.deletedAt.isNull());

        if (!keyword.isEmpty()) {
            builder.and(order.requirement.containsIgnoreCase(keyword));
        }

        if ("productId".equalsIgnoreCase(filterKey) && !filterValue.isEmpty()) {
            builder.and(order.productId.eq(UUID.fromString(filterValue)));
        }

        OrderSpecifier<?> sortOrder = sort.equalsIgnoreCase("desc")
            ? order.createdAt.desc()
            : order.createdAt.asc();

        List<Order> orders = queryFactory
            .select(order)
            .from(order)
            .where(builder)
            .orderBy(sortOrder,order.updatedAt.desc())
            .offset((long) page * size)
            .limit(size)
            .fetch();

        Long total = queryFactory
            .select(order.count())
            .from(order)
            .where(builder)
            .fetchOne();

        return Pagination.of(page,size,total,orders);
    }

    @Override
    public Optional<Order> findByIdAndHub(UUID userId, UUID hubId) {
        return jpaRepository.findOrderByIdAndManageHubIDAndDeletedAtIsNull(userId,hubId);
    }

    @Override
    public Pagination<Order> searchOrdersByHubId(String keyword, String filterKey, String filterValue,
        String sort, int page, int size, UUID hubId) {

        BooleanBuilder builder = new BooleanBuilder();
        QOrder order = QOrder.order;

//      삭제된 데이터는 검색 내용에서 제외
        builder.and(order.deletedAt.isNull());
        builder.and(order.manageHubID.eq(hubId));

        if (!keyword.isEmpty()) {
            builder.and(order.requirement.containsIgnoreCase(keyword));
        }

        if ("productId".equalsIgnoreCase(filterKey) && !filterValue.isEmpty()) {
            builder.and(order.productId.eq(UUID.fromString(filterValue)));
        }

        OrderSpecifier<?> sortOrder = sort.equalsIgnoreCase("desc")
            ? order.createdAt.desc()
            : order.createdAt.asc();

        List<Order> orders = queryFactory
            .select(order)
            .from(order)
            .where(builder)
            .orderBy(sortOrder,order.updatedAt.desc())
            .offset((long) page * size)
            .limit(size)
            .fetch();

        Long total = queryFactory
            .select(order.count())
            .from(order)
            .where(builder)
            .fetchOne();

        return Pagination.of(page,size,total,orders);
    }

    @Override
    public Optional<Order> findByUserId(UUID userId, UUID orderId) {
        return jpaRepository.findOrderByIdAndCreatedByAndDeletedAtIsNull(userId,orderId.toString());
    }

    @Override
    public Pagination<Order> searchOrdersByUserId(String keyword, String filterKey, String filterValue,
        String sort, int page, int size, UUID userId) {

        BooleanBuilder builder = new BooleanBuilder();
        QOrder order = QOrder.order;

//      삭제된 데이터는 검색 내용에서 제외
        builder.and(order.deletedAt.isNull());
        builder.and(order.createdBy.eq(userId.toString()));

        if (!keyword.isEmpty()) {
            builder.and(order.requirement.containsIgnoreCase(keyword));
        }

        if ("productId".equalsIgnoreCase(filterKey) && !filterValue.isEmpty()) {
            builder.and(order.productId.eq(UUID.fromString(filterValue)));
        }

        OrderSpecifier<?> sortOrder = sort.equalsIgnoreCase("desc")
            ? order.createdAt.desc()
            : order.createdAt.asc();

        List<Order> orders = queryFactory
            .select(order)
            .from(order)
            .where(builder)
            .orderBy(sortOrder,order.updatedAt.desc())
            .offset((long) page * size)
            .limit(size)
            .fetch();

        Long total = queryFactory
            .select(order.count())
            .from(order)
            .where(builder)
            .fetchOne();

        return Pagination.of(page,size,total,orders);
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        return jpaRepository.findOrderByIdAndDeletedAtIsNull(orderId);
    }
}
