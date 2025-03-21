package sparta.rooibos.hub.infrastructure.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.model.Pagination;
import sparta.rooibos.hub.domain.model.QHub;
import sparta.rooibos.hub.domain.respository.HubRepository;
import sparta.rooibos.hub.infrastructure.jpa.JpaHubRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubRepositoryImpl implements HubRepository {

    private final JpaHubRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Hub createHub(Hub hub) {
        return jpaRepository.save(hub);
    }

    @Override
    public Optional<Hub> getHub(UUID hubId) {
        return jpaRepository.getActiveHub(hubId);
    }

    // 허브 개수가 많지 않기 때문에 일반적인 페이지 넘버 방식 페이지네이션 구현
    @Override
    public Pagination<Hub> searchHub(String name, String region, int page, int size) {
        BooleanBuilder builder = new BooleanBuilder();
        QHub hub = QHub.hub;

        if (name != null) {
            builder.and(hub.name.contains(name));
        }

        if (region != null) {
            builder.and(hub.region.contains(region));
        }

        builder.and(hub.deletedAt.isNull());

        List<Hub> result = queryFactory
                .select(hub)
                .from(hub)
                .where(builder)
                .orderBy(hub.createdAt.desc(), hub.updatedAt.desc())
                .offset((long) page * size)
                .limit(size)
                .fetch();

        Long total = queryFactory
                .select(hub.count())
                .from(hub)
                .where(builder)
                .fetchOne();

        return Pagination.of(page, size, total != null ? total : 0, result);
    }
}
