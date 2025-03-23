package sparta.rooibos.route.application.port.out;

import sparta.rooibos.route.application.dto.response.Hub.HubClientResponse;

import java.util.UUID;

public interface HubClientService {
    HubClientResponse getHubByHubId(UUID hubId);
}
