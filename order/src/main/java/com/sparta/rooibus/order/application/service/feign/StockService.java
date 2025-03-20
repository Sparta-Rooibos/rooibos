package com.sparta.rooibus.order.application.service.feign;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public interface StockService {
    boolean checkStock(UUID uuid, int quantity);
}
