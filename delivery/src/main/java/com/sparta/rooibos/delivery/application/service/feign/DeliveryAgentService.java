package com.sparta.rooibos.delivery.application.service.feign;

import com.sparta.rooibos.delivery.application.dto.response.feign.deliverAgent.GetDeliverResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface DeliveryAgentService {

    GetDeliverResponse getDeliver(UUID departureHubId,String type,String role);

    void cancelDeliver(UUID deliver);
}
