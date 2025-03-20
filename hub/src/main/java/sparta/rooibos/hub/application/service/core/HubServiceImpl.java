package sparta.rooibos.hub.application.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.rooibos.hub.application.dto.hub.request.CreateHubRequest;
import sparta.rooibos.hub.application.dto.hub.request.SearchHubRequest;
import sparta.rooibos.hub.application.dto.hub.request.UpdateHubRequest;
import sparta.rooibos.hub.application.dto.hub.response.CreateHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.GetHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.SearchHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.UpdateHubResponse;
import sparta.rooibos.hub.application.service.exception.BusinessHubException;
import sparta.rooibos.hub.application.service.exception.custom.HubErrorCode;
import sparta.rooibos.hub.application.service.port.HubService;
import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.model.Pagination;
import sparta.rooibos.hub.domain.respository.HubRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
// TODO 도메인에 VO 만들어서 하나씩 보내고 있는 거 정리하기
public class HubServiceImpl implements HubService {

    private final HubRepository hubRepository;

    @Override
    @Transactional
    public CreateHubResponse createHub(CreateHubRequest createHubRequest) {
        Hub newHub = Hub.of(
                createHubRequest.name(),
                createHubRequest.region(),
                createHubRequest.address(),
                createHubRequest.latitude(),
                createHubRequest.longitude()
        );

        return CreateHubResponse.from(hubRepository.createHub(newHub));
    }

    @Cacheable(
            cacheNames = "getHub",
            key = "'getHub:' + #hubId"
    )
    @Override
    public GetHubResponse getHub(UUID hubId) {
        return GetHubResponse.from(getHubForServer(hubId));
    }

    @Override
    public UUID getHubIdByRegion(String region) {
        return hubRepository.getHubIdByRegion(region)
                .orElseThrow(() -> new BusinessHubException(HubErrorCode.HUB_NOT_FOUND));
    }

    @Cacheable(
            cacheNames = "searchHub",
            key = "'searchHub:' + (#searchHubRequest.name() ?: '') + ':' + (#searchHubRequest.region() ?: '')"
    )
    @Override
    public SearchHubResponse searchHub(SearchHubRequest searchHubRequest) {
        Pagination<Hub> hubPagination = hubRepository.searchHub(
                searchHubRequest.name(),
                searchHubRequest.region(),
                searchHubRequest.page(),
                searchHubRequest.size()
        );

        return SearchHubResponse.from(hubPagination);
    }

    @Override
    @Transactional
    public UpdateHubResponse updateHub(UUID hubId, UpdateHubRequest updateHubRequest) {
        Hub targetHub = getHubForServer(hubId);
        Hub sourceHub = Hub.of(
                updateHubRequest.name(),
                updateHubRequest.region(),
                updateHubRequest.address(),
                updateHubRequest.latitude(),
                updateHubRequest.longitude()
        );

        return UpdateHubResponse.from(targetHub.update(sourceHub));
    }

    @Override
    @Transactional
    public void deleteHub(UUID hubId) {
        Hub targetHub = getHubForServer(hubId);
        targetHub.delete();
    }

    /**
     * 서버에서만 사용하는 단일 조회 로직
     */
    private Hub getHubForServer(UUID hubId) {
        return hubRepository.getHub(hubId)
                .orElseThrow(() -> new BusinessHubException(HubErrorCode.HUB_NOT_FOUND));
    }
}
