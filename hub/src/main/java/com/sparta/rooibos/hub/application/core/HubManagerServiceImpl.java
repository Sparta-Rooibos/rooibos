package com.sparta.rooibos.hub.application.core;

import com.sparta.rooibos.hub.application.dto.hubManager.request.CreateHubManagerRequest;
import com.sparta.rooibos.hub.application.dto.hubManager.response.CreateHubManagerResponse;
import com.sparta.rooibos.hub.application.exception.BusinessHubManagerException;
import com.sparta.rooibos.hub.application.exception.custom.HubManagerErrorCode;
import com.sparta.rooibos.hub.application.port.in.HubManagerService;
import com.sparta.rooibos.hub.domain.model.HubManager;
import com.sparta.rooibos.hub.domain.respository.HubManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubManagerServiceImpl implements HubManagerService {

    private final HubManagerRepository hubManagerRepository;

    @Override
    public CreateHubManagerResponse createHubManager(CreateHubManagerRequest createHubManagerRequest) {
        HubManager savedHubManager =
                hubManagerRepository.createHubManager(createHubManagerRequest.userId(), createHubManagerRequest.hubId());

        return CreateHubManagerResponse.from(savedHubManager);
    }

    @Override
    public UUID getHubIdByUserId(UUID userId) {
        return hubManagerRepository.getHubIdByUserId(userId)
                .orElseThrow(() -> new BusinessHubManagerException(HubManagerErrorCode.HUB_MANAGER_NOT_FOUND));
    }

    @Override
    public UUID getHubIdByEmail(String email) {
        return hubManagerRepository.getHubIdByEmail(email)
                .orElseThrow(() -> new BusinessHubManagerException(HubManagerErrorCode.HUB_MANAGER_NOT_FOUND));
    }

    @Override
    public void deleteHubManager(UUID userId) {
        HubManager targetHubManager = getHubManager(userId);

        targetHubManager.delete();
    }

    private HubManager getHubManager(UUID userId) {
        return hubManagerRepository.getHubManagerByUserId(userId)
                .orElseThrow(() -> new BusinessHubManagerException(HubManagerErrorCode.HUB_MANAGER_NOT_FOUND));
    }
}
