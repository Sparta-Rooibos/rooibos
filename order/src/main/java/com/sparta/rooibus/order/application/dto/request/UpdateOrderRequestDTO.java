package com.sparta.rooibus.order.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateOrderRequestDTO(
    @NotNull UUID id,                  // 주문 ID
    UUID requestClientId,     // 요청(공급) 업체 ID
    UUID responseClientId,    // 수령 업체 ID
    UUID productId,           // 상품 ID
    @Min(1) Integer quantity,              // 수량
    String requirement       // 요청사항
) {

}
