package com.sparta.rooibos.product.infrastructure.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibos.product.domain.entity.Product;
import com.sparta.rooibos.product.domain.entity.QProduct;
import com.sparta.rooibos.product.domain.repository.QueryProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class QueryProductRepositoryImpl implements QueryProductRepository {
    private final JPAQueryFactory query;
    private final QProduct product = QProduct.product;

    @Override
    public Page<Product> getProductList(Pageable pageable,  UUID id, String name, String hubId, Boolean deleteCheck) {
        List<Product> content = query.select(product)
                .where(nullNameCheck(name),
                        nullIdCheck(id),
                        nullHubIdCheck(hubId),
                        nullCheckDeleted(deleteCheck)
                )
                .from(product)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query.select(product.count())
                .where(
                        nullNameCheck(name),
                        nullIdCheck(id),
                        nullHubIdCheck(hubId),
                        nullCheckDeleted(deleteCheck)
                )
                .from(product)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (content.isEmpty()) {
            return new PageImpl<>(content, pageable, 0);
        }

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private Predicate nullHubIdCheck(String hubId) {
        return hubId == null ? null : product.managedHubId.eq(hubId);
    }

    private Predicate nullCheckDeleted(Boolean deleteCheck) {
        return deleteCheck == null ? null : deleteCheck ? product.deleteBy.isNotNull() : product.deleteBy.isNull();
    }

    private Predicate nullIdCheck(UUID id) {
        return id == null ? null : product.id.eq(id);
    }

    private Predicate nullNameCheck(String name) {
        return name == null ? null : product.name.eq(name);
    }
}
