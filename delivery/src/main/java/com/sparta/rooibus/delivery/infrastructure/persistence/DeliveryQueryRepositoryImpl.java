package com.sparta.rooibus.delivery.infrastructure.persistence;

import static com.sparta.rooibus.delivery.domain.entity.QDelivery.delivery;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibus.delivery.application.dto.request.SearchDeliveryRequestDTO;
import com.sparta.rooibus.delivery.domain.entity.Delivery;
import com.sparta.rooibus.delivery.domain.model.DeliveryStatus;
import com.sparta.rooibus.delivery.domain.repository.DeliveryQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryQueryRepositoryImpl implements DeliveryQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Delivery> searchOrders(SearchDeliveryRequestDTO request, Pageable pageable) {
        BooleanExpression predicate = createPredicate(request);

        List<Delivery> deliveries = queryFactory
            .selectFrom(delivery)
            .where(predicate)
            .orderBy(delivery.createdAt.desc())  // 정렬 기준 설정 (예: createdAt 내림차순)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long totalCount = queryFactory
            .selectFrom(delivery)
            .where(predicate)
            .fetchCount();  // 전체 개수 계산

        return new PageImpl<>(deliveries, pageable, totalCount);
    }

    private BooleanExpression createPredicate(SearchDeliveryRequestDTO request) {
        BooleanExpression predicate = delivery.deletedAt.isNull();  // deleted_at이 null인 것만 검색

        // keyword 필드에 대한 검색
        if (request.keyword() != null && !request.keyword().isEmpty()) {
            predicate = predicate.and(delivery.address.containsIgnoreCase(request.keyword()));  // 예시: address 검색
        }

        // 필터링 키와 값에 따른 필터링
        if (request.filterKey() != null && request.filterValue() != null) {
            switch (request.filterKey()) {
                case "status":
                    predicate = predicate.and(delivery.status.eq(DeliveryStatus.valueOf(request.filterValue())));
                    break;
                // 다른 필터 키에 대해서도 처리할 수 있음
            }
        }

        return predicate;
    }
}
