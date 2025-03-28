package com.sparta.rooibos.deliverer.infrastructure.persistence;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibos.deliverer.domain.critia.DelivererSearchCriteria;
import com.sparta.rooibos.deliverer.domain.entity.Deliverer;
import com.sparta.rooibos.deliverer.domain.entity.QDeliverer;
import com.sparta.rooibos.deliverer.domain.model.Pagination;
import com.sparta.rooibos.deliverer.domain.repository.DelivererRepositoryCustom;
import com.sparta.rooibos.deliverer.infrastructure.util.AuthorizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DelivererRepositoryCustomImpl implements DelivererRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final AuthorizationUtil authorizationUtil;

    @Override
    public Pagination<Deliverer> searchDeliverers(DelivererSearchCriteria request) {
        QDeliverer deliverer = QDeliverer.deliverer;

        BooleanBuilder where = authorizationUtil.getPermissionWhereClause(deliverer);

        if (request.filter() != null && request.keyword() != null && !request.keyword().isBlank()) {
            switch (request.filter()) {
                case "username" -> where.and(deliverer.username.containsIgnoreCase(request.keyword()));
                case "email" -> where.and(deliverer.email.containsIgnoreCase(request.keyword()));
                case "type" -> where.and(deliverer.type.stringValue().equalsIgnoreCase(request.keyword()));
                case "hubId" -> {
                    try {
                        UUID hubId = UUID.fromString(request.keyword());
                        where.and(deliverer.hubId.eq(hubId));
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
        }

        OrderSpecifier<?> order = deliverer.username.asc();

        List<Deliverer> result = queryFactory
                .selectFrom(deliverer)
                .where(where)
                .orderBy(order)
                .offset((long) (request.page() - 1) * request.size())
                .limit(request.size())
                .fetch();

        Long total = queryFactory
                .select(deliverer.count())
                .from(deliverer)
                .where(where)
                .fetchOne();

        return Pagination.of(request.page(), request.size(), total != null ? total : 0, result);
    }
}


