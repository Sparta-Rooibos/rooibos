package com.sparta.rooibos.stock.application.service;

import com.sparta.rooibos.stock.application.dto.request.UpdateMultiStockListRequest;
import com.sparta.rooibos.stock.application.dto.request.UpdateMultiStockRequest;
import com.sparta.rooibos.stock.application.dto.request.UpdateStockRequest;
import com.sparta.rooibos.stock.domain.entity.Stock;
import com.sparta.rooibos.stock.domain.repository.StockRepository;
import com.sparta.rooibos.stock.domain.repository.StockRepositoryCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
class StockMultiServiceTest {

    @Mock
    private StockRepository stockRepository;


    @InjectMocks
    private StockService stockService;

    private String testEmail = "test@example.com";
    private UUID stockId1, stockId2, stockId3;
    private Stock stock1, stock2, stock3;

    @BeforeEach
    void setUp() {
        stockId1 = UUID.randomUUID();
        stockId2 = UUID.randomUUID();
        stockId3 = UUID.randomUUID();

        // 테스트용 Stock 객체 생성
        stock1 = new Stock(stockId1, 10);
        stock2 = new Stock(stockId2, 20);
        stock3 = new Stock(stockId3, 30);
    }

    @Test
    @DisplayName("다중 재고 업데이트 테스트")
    void updateMultiStock_성공() {
        // given
        UpdateMultiStockListRequest request1 = UpdateMultiStockListRequest.of(stockId1, 5);
        UpdateMultiStockListRequest request2 = UpdateMultiStockListRequest.of(stockId2, 10);
        UpdateMultiStockListRequest request3 = UpdateMultiStockListRequest.of(stockId3, -5);

        List<UpdateMultiStockListRequest> requestList = Arrays.asList(request1, request2, request3);
        UpdateMultiStockRequest multiRequest = new UpdateMultiStockRequest(requestList);

        when(stockRepository.findByIdsAndDeleteByIsNullWithLock(multiRequest.asIds()))
                .thenReturn(Arrays.asList(stock1, stock2, stock3));

        // when
        stockService.updateMultiStock(testEmail, multiRequest);

        // then
        assertThat(stock1.getProductQuantity()).isEqualTo(15); // 10 + 5
        assertThat(stock2.getProductQuantity()).isEqualTo(30); // 20 + 10
        assertThat(stock3.getProductQuantity()).isEqualTo(25); // 30 - 5

        verify(stockRepository, times(1)).findByIdsAndDeleteByIsNullWithLock(multiRequest.asIds());
    }

    @RepeatedTest(10000)
    @DisplayName("다중 재고 업데이트 동시성 테스트")
    void updateMultiStock_동시성_테스트() throws InterruptedException {
        // given
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        UpdateMultiStockListRequest request1 = UpdateMultiStockListRequest.of(stockId1, 1);
        UpdateMultiStockListRequest request2 = UpdateMultiStockListRequest.of(stockId2, 1);
        List<UpdateMultiStockListRequest> requestList = Arrays.asList(request1, request2);
        UpdateMultiStockRequest multiRequest = new UpdateMultiStockRequest(requestList);

        when(stockRepository.findByIdsAndDeleteByIsNullWithLock(multiRequest.asIds()))
                .thenReturn(Arrays.asList(stock1, stock2));

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.updateMultiStock(testEmail, multiRequest);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // then
        assertThat(stock1.getProductQuantity()).isEqualTo(20); // 10 + (1 * 10번)
        assertThat(stock2.getProductQuantity()).isEqualTo(30); // 20 + (1 * 10번)

        verify(stockRepository, times(threadCount)).findByIdsAndDeleteByIsNullWithLock(multiRequest.asIds());
    }

    @Test
    @DisplayName("부분 재고 업데이트 테스트")
    void updateMultiStock_부분_업데이트() {
        // given
        UpdateMultiStockListRequest request1 = UpdateMultiStockListRequest.of(stockId1, 5);
        UpdateMultiStockListRequest request2 = UpdateMultiStockListRequest.of(stockId2, 10);

        // stockId3는 요청에 포함되지 않음
        List<UpdateMultiStockListRequest> requestList = Arrays.asList(request1, request2);
        UpdateMultiStockRequest multiRequest = new UpdateMultiStockRequest(requestList);

        when(stockRepository.findByIdsAndDeleteByIsNullWithLock(multiRequest.asIds()))
                .thenReturn(Arrays.asList(stock1, stock2));

        // when
        stockService.updateMultiStock(testEmail, multiRequest);

        // then
        assertThat(stock1.getProductQuantity()).isEqualTo(15); // 10 + 5
        assertThat(stock2.getProductQuantity()).isEqualTo(30); // 20 + 10
        assertThat(stock3.getProductQuantity()).isEqualTo(30); // 변경 없음

        verify(stockRepository, times(1)).findByIdsAndDeleteByIsNullWithLock(multiRequest.asIds());
    }

    @Test
    @DisplayName("음수 재고 업데이트 예외 테스트")
    void updateMultiStock_음수_재고_예외() {
        // given
        UpdateMultiStockListRequest request1 = UpdateMultiStockListRequest.of(stockId1, -15); // 10 - 15 = -5 (불가능)
        List<UpdateMultiStockListRequest> requestList = Arrays.asList(request1);
        UpdateMultiStockRequest multiRequest = new UpdateMultiStockRequest(requestList);

        when(stockRepository.findByIdsAndDeleteByIsNullWithLock(multiRequest.asIds()))
                .thenReturn(Arrays.asList(stock1));

        // when & then
        assertThat(stock1.getProductQuantity()).isEqualTo(10);
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            stockService.updateMultiStock(testEmail, multiRequest);
        });

        verify(stockRepository, times(1)).findByIdsAndDeleteByIsNullWithLock(multiRequest.asIds());
    }
    @Test
    @DisplayName("데드락 시나리오 테스트")
    void testDeadlock() throws InterruptedException {
        // given
        // 두 개의 재고 생성
        UUID stockId1 = UUID.randomUUID();
        UUID stockId2 = UUID.randomUUID();

        Stock stock1 = Stock.create("test@example.com", "hub1", "product1", 100);
        Stock stock2 = Stock.create("test@example.com", "hub2", "product2", 100);

        // 리플렉션으로 ID 설정 (테스트용)
        setStockId(stock1, stockId1);
        setStockId(stock2, stockId2);

        stockRepository.save(stock1);
        stockRepository.save(stock2);

        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        AtomicBoolean deadlockDetected = new AtomicBoolean(false);

        // 첫 번째 스레드: stockId1 락 획득 후 stockId2 락 시도
        executorService.submit(() -> {
            try {
                // stockId1 락 획득
                UpdateStockRequest request1 = new UpdateStockRequest(10);
                stockService.updateStock("test1@example.com", stockId1, request1);

                // 다른 스레드가 stockId2에 락을 걸 시간을 줌
                Thread.sleep(500);

                // stockId2 락 시도 (이때 데드락 발생 가능)
                UpdateStockRequest request2 = new UpdateStockRequest(20);
                stockService.updateStock("test1@example.com", stockId2, request2);
            } catch (Exception e) {
                System.out.println("스레드 1 예외: " + e.getMessage());
                if (isDeadlockException(e)) {
                    deadlockDetected.set(true);
                }
            } finally {
                latch.countDown();
            }
        });

        // 두 번째 스레드: stockId2 락 획득 후 stockId1 락 시도
        executorService.submit(() -> {
            try {
                // 첫 번째 스레드가 stockId1에 락을 걸 시간을 줌
                Thread.sleep(100);

                // stockId2 락 획득
                UpdateStockRequest request1 = new UpdateStockRequest(15);
                stockService.updateStock("test2@example.com", stockId2, request1);

                // stockId1 락 시도 (이때 데드락 발생 가능)
                UpdateStockRequest request2 = new UpdateStockRequest(25);
                stockService.updateStock("test2@example.com", stockId1, request2);
            } catch (Exception e) {
                System.out.println("스레드 2 예외: " + e.getMessage());
                if (isDeadlockException(e)) {
                    deadlockDetected.set(true);
                }
            } finally {
                latch.countDown();
            }
        });

        // 최대 10초 대기
        latch.await(10, java.util.concurrent.TimeUnit.SECONDS);
        executorService.shutdownNow();

        // 데드락 또는 관련 예외가 발생했는지 확인
        assertTrue(deadlockDetected.get() || latch.getCount() > 0,
                "데드락이 발생하거나 타임아웃이 되어야 합니다.");
    }

    // 리플렉션을 사용하여 Stock 엔티티의 ID 설정
    private void setStockId(Stock stock, UUID id) {
        try {
            java.lang.reflect.Field idField = Stock.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(stock, id);
        } catch (Exception e) {
            throw new RuntimeException("ID 설정 실패", e);
        }
    }

    // 데드락 관련 예외인지 확인
    private boolean isDeadlockException(Throwable e) {
        Throwable cause = e;
        while (cause != null) {
            // 데드락 관련 메시지 확인
            if (cause.getMessage() != null &&
                    (cause.getMessage().contains("deadlock") ||
                            cause.getMessage().contains("Deadlock") ||
                            cause.getMessage().contains("lock wait timeout"))) {
                return true;
            }

            // 특정 예외 클래스 확인
            if (cause instanceof org.springframework.dao.DeadlockLoserDataAccessException ||
                    cause instanceof org.springframework.dao.CannotAcquireLockException ||
                    cause instanceof org.springframework.dao.PessimisticLockingFailureException) {
                return true;
            }

            // SQLException 확인
            if (cause instanceof java.sql.SQLException) {
                java.sql.SQLException sqlEx = (java.sql.SQLException) cause;
                // MySQL 데드락 에러 코드: 1213, PostgreSQL: 40P01
                if ("40P01".equals(sqlEx.getSQLState())) {
                    return true;
                }
            }

            cause = cause.getCause();
        }
        return false;
    }

    @Test
    @DisplayName("데드락 방지 테스트 - 항상 같은 순서로 락 획득")
    void testDeadlockPrevention() throws InterruptedException {
        // given
        // 두 개의 재고 생성
        UUID stockId1 = UUID.randomUUID();
        UUID stockId2 = UUID.randomUUID();

        // ID 순서로 정렬
        UUID smallerId = stockId1.compareTo(stockId2) < 0 ? stockId1 : stockId2;
        UUID largerId = stockId1.compareTo(stockId2) < 0 ? stockId2 : stockId1;

        Stock stock1 = Stock.create("test@example.com", "hub1", "product1", 100);
        Stock stock2 = Stock.create("test@example.com", "hub2", "product2", 100);

        setStockId(stock1, smallerId);
        setStockId(stock2, largerId);

        stockRepository.save(stock1);
        stockRepository.save(stock2);

        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        AtomicBoolean exceptionDetected = new AtomicBoolean(false);

        // 여러 스레드에서 동시에 두 재고를 동일한 순서로 업데이트
        for (int i = 0; i < threadCount; i++) {
            final int threadNumber = i;
            executorService.submit(() -> {
                try {
                    // 항상 ID가 작은 재고부터 락 획득 (데드락 방지)
                    UpdateStockRequest request1 = new UpdateStockRequest(1);
                    stockService.updateStock("test" + threadNumber + "@example.com", smallerId, request1);

                    UpdateStockRequest request2 = new UpdateStockRequest(1);
                    stockService.updateStock("test" + threadNumber + "@example.com", largerId, request2);
                } catch (Exception e) {
                    System.out.println("스레드 " + threadNumber + " 예외: " + e.getMessage());
                    exceptionDetected.set(true);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, java.util.concurrent.TimeUnit.SECONDS);
        executorService.shutdownNow();

        // 모든 스레드가 완료되었고 예외가 발생하지 않았는지 확인
        assertTrue(latch.getCount() == 0 && !exceptionDetected.get(),
                "모든 스레드가 예외 없이 완료되어야 합니다.");
    }
}