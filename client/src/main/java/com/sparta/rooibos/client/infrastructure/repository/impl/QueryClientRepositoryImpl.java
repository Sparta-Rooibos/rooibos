package com.sparta.rooibos.client.infrastructure.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibos.client.domain.critreia.ClientCriteria;
import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.entity.ClientType;
import com.sparta.rooibos.client.domain.entity.QClient;
import com.sparta.rooibos.client.domain.model.Pagination;
import com.sparta.rooibos.client.domain.repository.QueryClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryClientRepositoryImpl implements QueryClientRepository {
    private final JPAQueryFactory query;
    private final QClient client = QClient.client;

    @Override
    public Pagination<Client> searchClient(ClientCriteria criteria) {
        List<Client> result = query.select(client)
                .where(nullNameCheck(criteria.name()),
                        nullAddressCheck(criteria.address()),
                        nullTypeCheck(criteria.type()),
                        nullDeleteCheck(criteria.isDeleted())
                )
                .from(client)
                .offset((long) (criteria.page() - 1) * criteria.size())
                .limit(criteria.size())
                .fetch();

        Long total = query.select(client.count())
                .where(nullNameCheck(criteria.name()),
                        nullAddressCheck(criteria.address()),
                        nullTypeCheck(criteria.type()),
                        nullDeleteCheck(criteria.isDeleted())
                )
                .from(client).fetchOne();


        return Pagination.of(criteria.page(), criteria.size(), total != null ? total : 0, result);
    }

    private Predicate nullDeleteCheck(Boolean deleteCheck) {
        return deleteCheck == null ? null :
                deleteCheck
                        ? client.deleteAt.isNotNull()
                        : client.deleteAt.isNull();
    }

    private Predicate nullTypeCheck(String type) {
        return type != null ? client.type.eq(ClientType.valueOf(type)) : null;
    }

    private Predicate nullAddressCheck(String address) {
        return address != null ? client.clientAddress.like(address) : null;
    }

    private Predicate nullNameCheck(String name) {
        return name != null ? client.name.like(name) : null;
    }
}
