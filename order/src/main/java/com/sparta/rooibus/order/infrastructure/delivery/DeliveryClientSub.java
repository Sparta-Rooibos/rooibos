package com.sparta.rooibus.order.infrastructure.delivery;

import com.sparta.rooibus.order.application.dto.request.DeliveryRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponseDTO;
import com.sparta.rooibus.order.application.service.DeliveryService;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DeliveryClientSub implements DeliveryService {
    @Override
    public CreateOrderResponseDTO createDelivery(DeliveryRequestDTO request) {
        CreateOrderResponseDTO response =
            new CreateOrderResponseDTO(UUID.randomUUID(),UUID.randomUUID());
        System.out.println("createDelivery");
        return response;
    }
}
