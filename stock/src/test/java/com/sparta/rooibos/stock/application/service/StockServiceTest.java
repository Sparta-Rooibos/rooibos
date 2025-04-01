package com.sparta.rooibos.stock.application.service;

import com.sparta.rooibos.stock.domain.entity.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("재고 테스트")
class StockServiceTest {


    private static final String TEST_EMAIL = "test@apple.com";
    private static final String TEST_HUB_ID = "hubId";
    private static final String TEST_PRODUCT_ID = "productId";
    private static final int INITIAL_QUANTITY = 10;

    private Stock stock;


    @BeforeEach
    void setUp() {
        stock = Stock.create(
                TEST_EMAIL,
                TEST_HUB_ID,
                TEST_PRODUCT_ID,
                INITIAL_QUANTITY
        );
    }

    @Nested
    @DisplayName("재고 생성")
    class StockCreation {

        @Test
        @DisplayName("재고를 생성할 수 있다")
        void createStock() {
            // when
            stock.validateQuantity();
            // then
            assertThat(stock)
                    .extracting(
                            Stock::getProductQuantity,
                            Stock::getCreateBy,
                            Stock::getHubId,
                            Stock::getProductId
                    )
                    .containsExactly(
                            INITIAL_QUANTITY,
                            TEST_EMAIL,
                            TEST_HUB_ID,
                            TEST_PRODUCT_ID
                    );
        }
    }

    @Nested
    @DisplayName("재고 수정")
    class StockUpdate {

        @Test
        @DisplayName("재고 수량을 정상적으로 수정할 수 있다")
        void updateStock() {
            // when
            stock.update(-5, TEST_EMAIL);

            // then
            assertThat(stock)
                    .extracting(
                            Stock::getProductQuantity,
                            Stock::getUpdateBy
                    )
                    .containsExactly(5, TEST_EMAIL);
        }

        @Test
        @DisplayName("재고 수량이 0이 되면 예외가 발생한다")
        void cannotUpdateStockToZero() {
            // when & then
            assertThatThrownBy(() -> stock.update(-INITIAL_QUANTITY, TEST_EMAIL))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("수량은 0보다 작을 수 없습니다.");
        }

        @Test
        @DisplayName("재고 수량이 음수가 되면 예외가 발생한다")
        void cannotUpdateStockToNegative() {
            // when & then
            assertThatThrownBy(() -> stock.update(-(INITIAL_QUANTITY + 1), TEST_EMAIL))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("수량은 0보다 작을 수 없습니다.");
        }

        @Test
        @DisplayName("보유 수량보다 많은 재고를 사용할 수 없다")
        void cannotUpdateStockWithExcessQuantity() {
            // when & then
            assertThatThrownBy(() -> stock.update(-(INITIAL_QUANTITY * 2), TEST_EMAIL))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("수량은 0보다 작을 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("재고 삭제")
    class StockDeletion {

        @Test
        @DisplayName("재고를 삭제할 수 있다")
        void deleteStock() {
            // when
            stock.delete(TEST_EMAIL);

            // then
            assertThat(stock)
                    .extracting(Stock::getDeleteBy)
                    .isEqualTo(TEST_EMAIL);
        }
    }

    @Nested
    @DisplayName("재고 상태 검증")
    class StockStateValidation {

        @Test
        @DisplayName("재고 생성 시 수정 이력이 없다")
        void hasNoUpdateHistoryOnCreation() {
            // then
            assertThat(stock.getUpdateBy())
                    .isNull();
        }

        @Test
        @DisplayName("재고 생성 시 삭제 이력이 없다")
        void hasNoDeleteHistoryOnCreation() {
            // then
            assertThat(stock.getDeleteBy())
                    .isNull();
        }
    }
}