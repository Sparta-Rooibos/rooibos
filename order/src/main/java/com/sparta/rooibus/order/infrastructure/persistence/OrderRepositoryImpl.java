package com.sparta.rooibus.order.infrastructure.persistence;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.entity.QOrder;
import com.sparta.rooibus.order.domain.model.Pagination;
import com.sparta.rooibus.order.domain.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Pagination<Order> searchOrders(String keyword, String filterKey, String filterValue, String sort, int page, int size) {
        BooleanBuilder builder = new BooleanBuilder();
        QOrder order = QOrder.order;

        // 기본 조건: 삭제되지 않은 데이터 조회
        builder.and(order.deletedAt.isNull());

        List<Order> orders = queryFactory
            .select(order)
            .from(order)
            .where(builder)
            .orderBy(order.createdAt.desc(),order.updatedAt.desc())
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
}
