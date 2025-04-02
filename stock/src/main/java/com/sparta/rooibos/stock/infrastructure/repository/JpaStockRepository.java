package com.sparta.rooibos.stock.infrastructure.repository;

import com.sparta.rooibos.stock.domain.entity.Stock;
import com.sparta.rooibos.stock.domain.repository.StockRepository;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface JpaStockRepository extends JpaRepository<Stock, UUID>, StockRepository {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id = :uuid and s.deleteBy is null")
    Optional<Stock> findByIdAndDeleteByIsNullWithLock(@Param("uuid")UUID uuid);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id IN :ids and s.deleteBy is null ORDER BY s.id ASC")
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = "2000")
    })
    List<Stock> findByIdsAndDeleteByIsNullWithLock(@Param("ids")List<UUID> ids);

}
