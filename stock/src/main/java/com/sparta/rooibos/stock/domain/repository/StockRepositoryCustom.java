package com.sparta.rooibos.stock.domain.repository;


import com.sparta.rooibos.stock.domain.dto.criteria.StockPageCriteria;
import com.sparta.rooibos.stock.domain.entity.Pagination;
import com.sparta.rooibos.stock.domain.entity.Stock;

public interface StockRepositoryCustom {
    Pagination<Stock> searchStock(StockPageCriteria command);
}
