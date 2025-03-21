package com.sparta.rooibus.order.infrastructure.stock;

import com.sparta.rooibus.order.application.service.feign.StockService;
import java.util.Random;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class stockClientSub implements StockService {

    @Override
    public boolean checkStock(UUID uuid, int quantity) {
        return new Random().nextBoolean();
    }
}
