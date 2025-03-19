package com.sparta.rooibus.order.infrastructure.delivery;

import com.sparta.rooibus.order.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.order.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.order.application.service.DeliveryService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@Slf4j
public class DeliveryClientSub implements DeliveryService {
    @Override
    public CreateDeliveryResponse createDelivery(@RequestBody CreateDeliveryRequest request,@RequestHeader String role) {

        return new CreateDeliveryResponse(
            UUID.randomUUID(), // deliveryId
            "배송 중", // status
            UUID.randomUUID(), // departure (출발 허브 ID)
            UUID.randomUUID(), // arrival (목적지 허브 ID)
            "서울시 강남구 테헤란로 12345", // address
            UUID.randomUUID(), // recipient (수령인 ID)
            "slack_user_123", // slackAccount (수령인 슬랙 ID)
            UUID.randomUUID());
    }
}
