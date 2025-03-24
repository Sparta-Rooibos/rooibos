package sparta.rooibos.hub.application.port.in;

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

    boolean isExistingHub(UUID hubId);

    GetHubResponse getHubByRegion(String region);

    SearchHubResponse searchHub(String email, SearchHubRequest searchHubRequest);

    UpdateHubResponse updateHub(UUID hubId, UpdateHubRequest updateHubRequest);

    void deleteHub(UUID hubId);
}
