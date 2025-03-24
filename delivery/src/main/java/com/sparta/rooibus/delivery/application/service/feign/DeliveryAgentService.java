package com.sparta.rooibus.delivery.application.service.feign;

import com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent.GetDeliverRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent.GetHubDeliverRequest;
import com.sparta.rooibus.delivery.application.dto.response.feign.deliverAgent.GetDeliverResponse;
import com.sparta.rooibus.delivery.application.dto.response.feign.deliverAgent.GetHubDeliverResponse;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public interface DeliveryAgentService {

    GetDeliverResponse getDeliver(UUID departureHubId,String type,String role);

    void cancelDeliver(UUID deliver);
}
