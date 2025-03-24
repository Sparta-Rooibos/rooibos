package com.sparta.rooibos.stock.application.service;

import com.sparta.rooibos.stock.application.dto.request.UpdateStockRequest;
import com.sparta.rooibos.stock.domain.entity.Stock;
import com.sparta.rooibos.stock.domain.repository.StockRepository;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


class StockServiceTest {

    @RepeatedTest(100)
    public void testConcurrency() throws InterruptedException {
        UUID stockId = UUID.randomUUID();
        Stock stock = new Stock(stockId, 100);
        StockRepository mockStockRepository = Mockito.mock(StockRepository.class);
        Mockito.when(mockStockRepository.findByIdAndDeleteByIsNullWithLock(stockId)).thenReturn(Optional.of(stock));

        StockService stockService = new StockService(mockStockRepository, null);

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        // 두 스레드에서 동시에 재고 차감 시도
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                stockService.updateStock(null, stockId, new UpdateStockRequest(-1)); // 첫 번째 스레드
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);

        assertEquals(0, stock.getProductQuantity());
    }
}