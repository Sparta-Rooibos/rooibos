package com.sparta.rooibus.delivery.infrastructure.deliverAgent;

import com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent.GetDeliverRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent.GetHubDeliverRequest;
import com.sparta.rooibus.delivery.application.dto.response.feign.deliverAgent.GetDeliverResponse;
import com.sparta.rooibus.delivery.application.dto.response.feign.deliverAgent.GetHubDeliverResponse;
import com.sparta.rooibus.delivery.application.service.feign.DeliveryAgentService;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DeliveryAgentClientSub implements DeliveryAgentService {

    @Override
    public GetDeliverResponse getDeliver(GetDeliverRequest from) {
        return new GetDeliverResponse(UUID.randomUUID());
    }

    @Override
    public GetHubDeliverResponse getHubDeliver(GetHubDeliverRequest request) {
        return new GetHubDeliverResponse(UUID.randomUUID());
    }
}
