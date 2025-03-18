package com.sparta.rooibos.stock.infrastructure.repository;

import com.sparta.rooibos.stock.domain.repository.StockRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStockRepository extends JpaRepository<Object,Object>, StockRepository {
}
