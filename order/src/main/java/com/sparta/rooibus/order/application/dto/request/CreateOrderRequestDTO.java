package com.sparta.rooibus.order.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateOrderRequestDTO(
    @NotNull UUID requestClientId,   // 공급 업체 ID
    @NotNull UUID responseClientId,  // 수령 업체 ID
    @NotNull UUID productId,         // 상품 ID
    @Min(1) int quantity,            // 수량
    String requirement               // 요청사항
) {
}
