package com.sparta.rooibos.order.application.service;

import java.util.UUID;

public record GetStockResponse(
    UUID id,
    String hubId,
    String productId,
    int productQuantity
) { }
