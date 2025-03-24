package com.sparta.rooibos.client.infrastructure.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.entity.QClient;
import com.sparta.rooibos.client.domain.entity.ClientType;
import com.sparta.rooibos.client.domain.repository.QueryClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query.select(client.count())
                .where(
                        nullNameCheck(name),
                        nullAddressCheck(address),
                        nullTypeCheck(type),
                        nullDeleteCheck(deleteCheck))
                .from(client)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (content.isEmpty()) {
            return new PageImpl<>(content, pageable, 0);
        }

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

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
