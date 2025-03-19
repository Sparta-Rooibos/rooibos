package com.sparta.rooibos.stock.domain.dto.criteria;

import java.util.UUID;

public record StockPageCriteria(UUID id, String productId, String hubId, Boolean isDeleted, String sort, Integer page, Integer size) {
}
