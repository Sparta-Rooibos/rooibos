package com.sparta.rooibus.order.application.service.feign;

import com.sparta.rooibus.order.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibus.order.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.order.application.dto.request.CreateDeliveryRequest;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface DeliveryService {
    ResponseEntity<CreateDeliveryResponse> createDelivery(UUID userId, String username, String role,CreateDeliveryRequest request);

    void cancelDelivery(UpdateDeliveryRequest request, String feignRole);

    ResponseEntity<UUID> deleteDelivery(UUID deliveryId, String feignRole);
}
