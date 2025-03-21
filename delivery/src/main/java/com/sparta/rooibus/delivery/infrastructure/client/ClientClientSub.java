package com.sparta.rooibus.delivery.infrastructure.client;

import com.sparta.rooibus.delivery.application.dto.request.feign.client.GetClientManagerRequest;
import com.sparta.rooibus.delivery.application.dto.request.feign.client.GetClientRequest;
import com.sparta.rooibus.delivery.application.dto.response.feign.client.GetClientManagerResponse;
import com.sparta.rooibus.delivery.application.dto.response.feign.client.GetClientResponse;
import com.sparta.rooibus.delivery.application.service.feign.ClientService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ClientClientSub implements ClientService {

    @Override
    public GetClientResponse getClient(GetClientRequest request) {
        return new GetClientResponse(
            UUID.randomUUID(),
            "Sample Company",
            "서울시 강남구 테헤란로 123",
            "PRODUCE",
            new GetClientResponse.ManageHubResponse(
                UUID.randomUUID(),
                "서울특별시 센터",
                "서울",
                "서울특별시 송파구 송파대로 55"
            ),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    @Override
    public GetClientManagerResponse getClientManager(GetClientManagerRequest request) {
        return new GetClientManagerResponse(UUID.randomUUID());
    }
}
