package com.sparta.rooibus.delivery.application.service.feign;

import com.sparta.rooibus.delivery.application.dto.request.feign.client.GetClientManagerRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.client.GetClientRequest;
import com.sparta.rooibus.delivery.application.dto.response.feign.client.GetClientManagerResponse;
import com.sparta.rooibus.delivery.application.dto.response.feign.client.GetClientResponse;
import org.springframework.stereotype.Component;

@Component
public interface ClientService {
    GetClientResponse getClient(GetClientRequest request);
    GetClientManagerResponse getClientManager(GetClientManagerRequest request);
}
