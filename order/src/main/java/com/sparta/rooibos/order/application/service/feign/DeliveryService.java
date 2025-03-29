package com.sparta.rooibos.order.application.service.feign;

import com.sparta.rooibos.order.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibos.order.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibos.order.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibos.order.infrastructure.delivery.UpdateDeliveryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface DeliveryService {
    ResponseEntity<CreateDeliveryResponse> createDelivery(UUID userId, String username, String role,CreateDeliveryRequest request);

    ResponseEntity<UpdateDeliveryResponse> cancelDelivery(UpdateDeliveryRequest request, String feignRole);

    ResponseEntity<UUID> deleteDelivery(UUID deliveryId, String feignRole);
}
