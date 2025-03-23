package com.sparta.rooibos.stock.application.service;

import com.sparta.rooibos.stock.application.custom.StockErrorCode;
import com.sparta.rooibos.stock.application.dto.request.CreateStockRequest;
import com.sparta.rooibos.stock.application.dto.request.SearchStockRequest;
import com.sparta.rooibos.stock.application.dto.request.UpdateStockRequest;
import com.sparta.rooibos.stock.application.dto.response.CreateStockResponse;
import com.sparta.rooibos.stock.application.dto.response.GetStockResponse;
import com.sparta.rooibos.stock.application.dto.response.SearchStockResponse;
import com.sparta.rooibos.stock.application.exception.BusinessStockException;
import com.sparta.rooibos.stock.domain.entity.Stock;
import com.sparta.rooibos.stock.domain.model.Pagination;
import com.sparta.rooibos.stock.domain.repository.StockRepository;
import com.sparta.rooibos.stock.domain.repository.StockRepositoryCustom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository repository;
    private final StockRepositoryCustom queryRepository;

    public SearchStockResponse searchStock(SearchStockRequest request) {
        Pagination<Stock> stock = queryRepository.searchStock(request.toCriteria());
        return SearchStockResponse.from(stock);
    }

    public GetStockResponse getStock(UUID stockId) {
        Stock stock = repository.findByIdAndDeleteByIsNull(stockId).orElseThrow(() -> new BusinessStockException(StockErrorCode.STOCK_NOT_FOUND));
        return GetStockResponse.from(stock);
    }


    @Transactional
    public CreateStockResponse createStock(String email, CreateStockRequest request) {
        Stock stock = repository.save(request.toEntity(email));
        stock.validateQuantity();
        return CreateStockResponse.from(stock);
    }

    @Transactional
    public void updateStock(String email, UUID stockId, UpdateStockRequest request) {
        // 비관적 락을 사용하여 재고 조회 (PESSIMISTIC_WRITE로 잠금)
        Stock stock = repository.findByIdAndDeleteByIsNullWithLock(stockId)
                .orElseThrow(() -> new BusinessStockException(StockErrorCode.STOCK_NOT_FOUND));

        // 재고 차감
        stock.update(request.quantity(), email);

        // 재고 업데이트 후 저장
        repository.save(stock);
    }

    @Transactional
    public void deleteStock(String email, UUID stockId) {
        Stock stock = repository.findByIdAndDeleteByIsNull(stockId).orElseThrow(() -> new BusinessStockException(StockErrorCode.STOCK_NOT_FOUND));
        stock.delete(email);
    }
}