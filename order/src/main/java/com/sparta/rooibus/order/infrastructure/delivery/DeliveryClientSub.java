package com.sparta.rooibus.order.infrastructure.delivery;

import com.sparta.rooibus.order.application.dto.request.DeliveryRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponseDTO;
import com.sparta.rooibus.order.application.service.DeliveryService;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DeliveryClientSub implements DeliveryService {
// 배달 서비스가 아직 준비 안되었을때 임시 서비스 로직 DeliveryService가 이걸 사용
    @Override
    public CreateOrderResponseDTO createDelivery(DeliveryRequestDTO request) {
        CreateOrderResponseDTO response =
            new CreateOrderResponseDTO(UUID.randomUUID(),UUID.randomUUID());
        System.out.println("createDelivery");
        return response;
    }
}
