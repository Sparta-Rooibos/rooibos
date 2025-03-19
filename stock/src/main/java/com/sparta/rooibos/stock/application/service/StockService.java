package com.sparta.rooibos.stock.application.service;

import com.sparta.rooibos.stock.application.custom.StockErrorCode;
import com.sparta.rooibos.stock.application.dto.request.CreateStockRequest;
import com.sparta.rooibos.stock.application.dto.request.SearchStockRequest;
import com.sparta.rooibos.stock.application.dto.request.UpdateStockRequest;
import com.sparta.rooibos.stock.application.dto.response.CreateStockResponse;
import com.sparta.rooibos.stock.application.dto.response.GetStockResponse;
import com.sparta.rooibos.stock.application.dto.response.SearchStockResponse;
import com.sparta.rooibos.stock.application.exception.BusinessStockException;
import com.sparta.rooibos.stock.domain.model.Pagination;
import com.sparta.rooibos.stock.domain.entity.Stock;
import com.sparta.rooibos.stock.domain.repository.StockRepositoryCustom;
import com.sparta.rooibos.stock.domain.repository.StockRepository;
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
    public CreateStockResponse createStock(CreateStockRequest request) {
        Stock stock = repository.save(request.toEntity());
        stock.validateQuantity();
        return CreateStockResponse.from(stock);
    }

    @Transactional
    public void updateStock(UUID stockId, UpdateStockRequest request) {
        Stock stock = repository.findByIdAndDeleteByIsNull(stockId).orElseThrow(() -> new BusinessStockException(StockErrorCode.STOCK_NOT_FOUND));
        stock.update(request.quantity());
    }

    @Transactional
    public void deleteStock(UUID stockId) {
        Stock stock = repository.findByIdAndDeleteByIsNull(stockId).orElseThrow(() -> new BusinessStockException(StockErrorCode.STOCK_NOT_FOUND));
        stock.delete("계정 아이디");
    }
}