package com.sparta.rooibos.stock.application.service;

import com.sparta.rooibos.stock.domain.entity.Stock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("재고 테스트")
class StockServiceTest {


    @Test
    @DisplayName("재고 생성")
    void createStock() {
        Stock stock = Stock.create(
                "test@apple.com",
                "hubId",
                "productId",
                10
        );

        Assertions.assertThat(stock.getProductQuantity()).isEqualTo(10);
        Assertions.assertThat(stock.getCreateBy()).isNotNull();
    }

    @Test
    @DisplayName("재고 수정 (정상)")
    void updateStock() {
        Stock stock = Stock.create(
                "test@apple.com",
                "hubId",
                "productId",
                10
        );

        stock.update(-5, "test@apple.com");
        Assertions.assertThat(stock.getProductQuantity()).isEqualTo(5);
        Assertions.assertThat(stock.getUpdateBy()).isNotNull();
    }

    @Test
    @DisplayName("수량을 가진것보다 재고에서 많이 사용이 되어지는 경우")
    void minusUpdateStock() {
        Stock stock = Stock.create(
                "test@apple.com",
                "hubId",
                "productId",
                10
        );

        Assertions.assertThatThrownBy(() -> {
                    stock.update(-20, "test@apple.com");
                    throw new IllegalArgumentException("수량은 0보다 작을 수 없습니다.");
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 0보다 작을 수 없습니다.");
    }


    @Test
    @DisplayName("재고가 삭제가 되어지는 경우")
    void deleteStock() {
        Stock stock = Stock.create(
                "test@apple.com",
                "hubId",
                "productId",
                10
        );
        stock.delete("test@apple.com");
        Assertions.assertThat(stock.getDeleteBy()).isNotNull();
    }
}