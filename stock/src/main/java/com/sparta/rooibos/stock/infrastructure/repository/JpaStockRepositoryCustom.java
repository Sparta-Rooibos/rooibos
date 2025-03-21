package com.sparta.rooibos.stock.infrastructure.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibos.stock.domain.criteria.StockCriteria;
import com.sparta.rooibos.stock.domain.model.Pagination;
import com.sparta.rooibos.stock.domain.entity.QStock;
import com.sparta.rooibos.stock.domain.entity.Stock;
import com.sparta.rooibos.stock.domain.repository.StockRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaStockRepositoryCustom implements StockRepositoryCustom {
    private final JPAQueryFactory query;
    private final QStock stock = QStock.stock;

    @Override
    public Pagination<Stock> searchStock(StockCriteria command) {
        List<Stock> content = query.select(stock)
                .where(
                        nullIdCheck(command.id()),
                        nullProductIdCheck(command.productId()),
                        nullHubIdCheck(command.hubId()),
                        nullCheckDeleted(command.isDeleted())
                )
                .from(stock)
                .offset((long) (command.page() - 1) * command.size())
                .limit(command.size())
                .fetch();

        Long total = query.select(stock.count())
                .where(
                        nullIdCheck(command.id()),
                        nullProductIdCheck(command.productId()),
                        nullHubIdCheck(command.hubId()),
                        nullCheckDeleted(command.isDeleted())
                )
                .from(stock).fetchOne();


        return Pagination.of(command.page(), command.size(), total != null ? total : 0, content);
    }


    private Predicate nullIdCheck(UUID id) {
        return id == null ? null : stock.id.eq(id);
    }

    private Predicate nullProductIdCheck(String productId) {
        return productId == null ? null : stock.productId.like(productId);
    }

    private Predicate nullHubIdCheck(String hubId) {
        return hubId == null ? null : stock.hubId.eq(hubId);
    }

    private Predicate nullCheckDeleted(Boolean deleted) {
        return deleted == null ? null : deleted ? stock.deleteBy.isNotNull() : stock.deleteBy.isNull();
    }


}
