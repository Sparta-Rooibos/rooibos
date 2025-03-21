package com.sparta.rooibus.order.infrastructure.persistence;

import static com.sparta.rooibus.order.domain.entity.QOrder.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibus.order.application.dto.request.SearchOrderRequestDTO;
import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.model.OrderStatus;
import com.sparta.rooibus.order.domain.repository.OrderQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Order> searchOrders(SearchOrderRequestDTO request, Pageable pageable) {
        BooleanExpression predicate = createPredicate(request);

        List<Order> orders = queryFactory
            .selectFrom(order)
            .where(predicate)
            .orderBy(order.createdAt.desc())  // 정렬 기준 설정 (예: createdAt 내림차순)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long totalCount = queryFactory
            .selectFrom(order)
            .where(predicate)
            .fetchCount();  // 전체 개수 계산

        return new PageImpl<>(orders, pageable, totalCount);
    }

    private BooleanExpression createPredicate(SearchOrderRequestDTO request) {
        BooleanExpression predicate = order.deletedAt.isNull();  // deleted_at이 null인 것만 검색

        // keyword 필드에 대한 검색
        if (request.keyword() != null && !request.keyword().isEmpty()) {
            predicate = predicate.and(order.requirement.containsIgnoreCase(request.keyword()));  // 예시: requirement로 검색
        }

        // 필터링 키와 값에 따른 필터링
        if (request.filterKey() != null && request.filterValue() != null) {
            switch (request.filterKey()) {
                case "status":
                    predicate = predicate.and(order.status.eq(OrderStatus.valueOf(request.filterValue())));
                    break;
                // 다른 필터 키에 대해서도 처리할 수 있음
            }
        }

        return predicate;
    }
}
