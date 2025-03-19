package com.sparta.rooibos.stock.infrastructure.repository;

import com.sparta.rooibos.stock.domain.entity.Stock;
import com.sparta.rooibos.stock.domain.repository.StockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaStockRepository extends JpaRepository<Stock, UUID>, StockRepository {

}
