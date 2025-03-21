package sparta.rooibos.hub.application.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.rooibos.hub.application.dto.hubManager.request.CreateHubManagerRequest;
import sparta.rooibos.hub.application.dto.hubManager.response.CreateHubManagerResponse;
import sparta.rooibos.hub.application.dto.hubManager.response.GetHubManagerHubIdResponse;
import sparta.rooibos.hub.application.service.exception.BusinessHubManagerException;
import sparta.rooibos.hub.application.service.exception.custom.HubManagerErrorCode;
import sparta.rooibos.hub.application.service.port.in.HubManagerService;
import sparta.rooibos.hub.domain.model.HubManager;
import sparta.rooibos.hub.domain.respository.HubManagerRepository;

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
    public GetHubManagerHubIdResponse getHubIdByUserId(UUID userId) {
        HubManager targetHubManger = getHubManagerByUserId(userId);

        return GetHubManagerHubIdResponse.from(targetHubManger);
    }

    @Override
    public void deleteHubManager(UUID userId) {
        HubManager targetHubManager = getHubManagerByUserId(userId);

        targetHubManager.delete();
    }

    private HubManager getHubManagerByUserId(UUID userId) {
        return hubManagerRepository.getHubManagerByUserId(userId)
                .orElseThrow(() -> new BusinessHubManagerException(HubManagerErrorCode.HUB_MANAGER_NOT_FOUND));
    }
}
