package com.sparta.rooibos.stock.domain.repository;

import com.sparta.rooibos.stock.domain.entity.Stock;

import java.util.Optional;
import java.util.UUID;

public interface StockRepository {
    Stock save(Stock stock);
    Optional<Stock> findByIdAndDeleteByIsNull(UUID uuid);
    Optional<Stock> findByProductIdAndDeleteByIsNull(String productId);
    Optional<Stock> findByIdAndDeleteByIsNullWithLock(UUID uuid);
}
