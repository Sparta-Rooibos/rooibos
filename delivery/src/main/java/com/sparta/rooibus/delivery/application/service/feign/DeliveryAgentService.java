package com.sparta.rooibus.delivery.application.service.feign;

import com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent.GetDeliverRequest;
import com.sparta.rooibus.delivery.application.dto.response.feign.deliverAgent.GetDeliverResponse;
import org.springframework.stereotype.Component;

@Component
public interface DeliveryAgentService {

    GetDeliverResponse getDeliver(GetDeliverRequest from);
}
