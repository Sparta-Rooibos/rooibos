package com.sparta.rooibos.hub.application.port.in;

import com.sparta.rooibos.hub.application.dto.hubManager.request.CreateHubManagerRequest;
import com.sparta.rooibos.hub.application.dto.hubManager.response.CreateHubManagerResponse;

import java.util.UUID;

public interface HubManagerService {

    CreateHubManagerResponse createHubManager(CreateHubManagerRequest createHubManagerRequest);

    UUID getHubIdByUserId(UUID userId);

    UUID getHubIdByEmail(String email);

    void deleteHubManager(UUID userId);
}
