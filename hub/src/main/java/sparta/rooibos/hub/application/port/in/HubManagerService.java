package sparta.rooibos.hub.application.port.in;

import sparta.rooibos.hub.application.dto.hubManager.request.CreateHubManagerRequest;
import sparta.rooibos.hub.application.dto.hubManager.response.CreateHubManagerResponse;
import sparta.rooibos.hub.application.dto.hubManager.response.GetHubManagerHubIdResponse;

import java.util.UUID;

public interface HubManagerService {

    CreateHubManagerResponse createHubManager(CreateHubManagerRequest createHubManagerRequest);

    GetHubManagerHubIdResponse getHubIdByUserId(UUID userId);

    GetHubManagerHubIdResponse getHubIdByEmail(String email);

    void deleteHubManager(UUID userId);
}
