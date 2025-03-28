package com.sparta.rooibos.user.infrastructure.persistence;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibos.user.application.dto.request.UserSearchRequest;
import com.sparta.rooibos.user.domain.critria.UserSearchCriteria;
import com.sparta.rooibos.user.domain.entity.QUser;
import com.sparta.rooibos.user.domain.entity.Role;
import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.model.Pagination;
import com.sparta.rooibos.user.domain.repository.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Pagination<User> searchUsers(UserSearchCriteria criteria) {
        QUser user = QUser.user;
        BooleanBuilder whereClause = new BooleanBuilder();

        // 필터별 키워드 검색
        if (criteria.keyword() != null && !criteria.keyword().isBlank()) {
            switch (criteria.filter()) {
                case "username" -> whereClause.and(user.username.containsIgnoreCase(criteria.keyword()));
                case "email" -> whereClause.and(user.email.containsIgnoreCase(criteria.keyword()));
                case "slackAccount" -> whereClause.and(user.slackAccount.containsIgnoreCase(criteria.keyword()));
                case "role" -> {
                    try {
                        Role role = Role.valueOf(criteria.keyword().toUpperCase());
                        whereClause.and(user.role.eq(role));
                    } catch (IllegalArgumentException ignored) {
                        whereClause.and(user.role.isNull());
                    }
                }
            }
        }

        // 정렬 설정 (기본값: desc)
        OrderSpecifier<?> orderSpecifier = "asc".equalsIgnoreCase(criteria.sort())
                ? user.createdAt.asc()
                : user.createdAt.desc();

        List<User> results = queryFactory
                .selectFrom(user)
                .where(whereClause)
                .orderBy(orderSpecifier)
                .offset((long) (criteria.page() - 1) * criteria.size())
                .limit(criteria.size())
                .fetch();

        Long total = queryFactory
                .select(user.count())
                .from(user)
                .where(whereClause)
                .fetchOne();

        return Pagination.of(criteria.page(), criteria.size(), total != null ? total : 0, results);
    }
}
