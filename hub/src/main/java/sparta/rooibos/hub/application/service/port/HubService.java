package sparta.rooibos.hub.application.service.port;

import sparta.rooibos.hub.application.dto.hub.request.CreateHubRequest;
import sparta.rooibos.hub.application.dto.hub.request.SearchHubRequest;
import sparta.rooibos.hub.application.dto.hub.request.UpdateHubRequest;
import sparta.rooibos.hub.application.dto.hub.response.CreateHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.GetHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.SearchHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.UpdateHubResponse;

import java.util.UUID;

public interface HubService {
    CreateHubResponse createHub(CreateHubRequest createHubRequest);

    GetHubResponse getHub(UUID hubId);

    SearchHubResponse searchHub(SearchHubRequest searchHubRequest);

    UpdateHubResponse updateHub(UUID hubId, UpdateHubRequest updateHubRequest);

    void deleteHub(UUID hubId);
}
