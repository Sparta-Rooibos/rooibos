package com.sparta.rooibos.stock.infrastructure.repository;

import com.sparta.rooibos.stock.domain.entity.Stock;
import com.sparta.rooibos.stock.domain.repository.StockRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaStockRepository extends JpaRepository<Stock, UUID>, StockRepository {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id = :uuid and s.deleteBy is null")
    Optional<Stock> findByIdAndDeleteByIsNullWithLock(UUID uuid);
}
