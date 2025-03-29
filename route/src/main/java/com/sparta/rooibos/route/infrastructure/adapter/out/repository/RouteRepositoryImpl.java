package com.sparta.rooibos.route.infrastructure.adapter.out.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibos.route.domain.model.QRoute;
import com.sparta.rooibos.route.domain.model.Route;
import com.sparta.rooibos.route.domain.repository.RouteRepository;
import com.sparta.rooibos.route.infrastructure.jpa.JpaRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepository {

    private final JpaRouteRepository jpaRouteRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Route createRoute(Route route) {
        return jpaRouteRepository.save(route);
    }

    @Override
    public Optional<Route> getRoute(UUID routeId) {
        return jpaRouteRepository.getRoute(routeId);
    }

    @Override
    public List<Route> getAllRoutes() {
        return jpaRouteRepository.getAllRoutes();
    }

    @Override
    public List<Route> getAllRoutesByHubId(UUID hubId) {
        return jpaRouteRepository.getAllRoutesByHubId(hubId);
    }

    /**
     * 다익스트라 알고리즘을 적용한 전체 경로가 아닌,
     * 시작 허브와 도착 허브로 구성된 허브 경로를 검색.
     * 이때 distance, timeCost 로 경로를 정렬할 수 있음
     * 경로가 많진 않겠지만, 무한 스크롤로 구현
     */
    @Override
    public List<Route> searchRoute(
            UUID fromHubId,
            UUID toHubId,
            String sort,
            int size,
            LocalDateTime lastCreatedAt,
            Integer lastDistance,
            Integer lastTimeCost
    ) {
        QRoute route = QRoute.route;
        BooleanBuilder builder = new BooleanBuilder();
        OrderSpecifier<?> order = checkSort(sort);

        builder.and(checkFromHubId(fromHubId));
        builder.and(checkToHubId(toHubId));
        builder.and(checkPaginationCriterion(lastCreatedAt, lastDistance, lastTimeCost));

        return queryFactory
                .selectFrom(route)
                .where(builder)
                .orderBy(order)
                .limit(size)
                .fetch();
    }

    private BooleanExpression checkFromHubId(UUID fromHubId) {
        return fromHubId == null ? null : QRoute.route.fromHubId.eq(fromHubId);
    }

    private BooleanExpression checkToHubId(UUID toHubId) {
        return toHubId == null ? null : QRoute.route.toHubId.eq(toHubId);
    }

    private OrderSpecifier<?> checkSort(String sort) {
        if (sort.equals("distance")) {
            return QRoute.route.distance.asc();
        }

        else if (sort.equalsIgnoreCase("timeCost")) {
            return QRoute.route.timeCost.asc();
        }

        return QRoute.route.createdAt.desc();
    }

    // TODO distance, timeCost 타입 숫자형으로 리팩토링 필요
    private BooleanExpression checkPaginationCriterion(
            LocalDateTime lastCreatedAt,
            Integer lastDistance,
            Integer lastTimeCost
    ) {
        if (lastDistance != null) {
            return QRoute.route.distance.gt(lastDistance) ;
        }

        else if (lastTimeCost != null) {
            return QRoute.route.timeCost.gt(lastTimeCost) ;
        }

        else if (lastCreatedAt != null) {
            return QRoute.route.createdAt.loe(lastCreatedAt);
        }

        return null; // 첫 페이지인 경우
    }
}
