package com.sparta.rooibos.stock.domain.repository;


import com.sparta.rooibos.stock.domain.criteria.StockCriteria;
import com.sparta.rooibos.stock.domain.model.Pagination;
import com.sparta.rooibos.stock.domain.entity.Stock;

public interface StockRepositoryCustom {
    Pagination<Stock> searchStock(StockCriteria command);
}
