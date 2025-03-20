package com.sparta.rooibus.order.application.service.feign;

import com.sparta.rooibus.order.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.order.application.dto.request.CreateDeliveryRequest;
import org.springframework.stereotype.Component;

@Component
public interface DeliveryService {
    CreateDeliveryResponse createDelivery(CreateDeliveryRequest request,String role);
}
