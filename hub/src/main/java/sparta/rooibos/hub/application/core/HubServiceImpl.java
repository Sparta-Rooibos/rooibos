package sparta.rooibos.hub.application.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.rooibos.hub.application.dto.GeoLocation.request.GetCoordinatesRequest;
import sparta.rooibos.hub.application.dto.GeoLocation.response.GetCoordinatesResponse;
import sparta.rooibos.hub.application.dto.hub.request.CreateHubRequest;
import sparta.rooibos.hub.application.dto.hub.request.SearchHubRequest;
import sparta.rooibos.hub.application.dto.hub.request.UpdateHubRequest;
import sparta.rooibos.hub.application.dto.hub.response.CreateHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.GetHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.SearchHubResponse;
import sparta.rooibos.hub.application.dto.hub.response.UpdateHubResponse;
import sparta.rooibos.hub.application.dto.message.HubDeletedActivity;
import sparta.rooibos.hub.application.exception.BusinessHubException;
import sparta.rooibos.hub.application.exception.custom.HubErrorCode;
import sparta.rooibos.hub.application.port.in.HubService;
import sparta.rooibos.hub.application.port.out.GeoLocationService;
import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.model.Pagination;
import sparta.rooibos.hub.domain.respository.HubRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

    private final GeoLocationService geoLocationService;
    private final HubRepository hubRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public CreateHubResponse createHub(CreateHubRequest createHubRequest) {
        Hub newHub = Hub.of(
                createHubRequest.name(),
                createHubRequest.region(),
                createHubRequest.address()
        );

        getAndSetCoordinates(newHub);

        return CreateHubResponse.from(hubRepository.createHub(newHub));
    }

    // TODO compose.yml 작성하기
//    @Cacheable(
//            cacheNames = "getHub",
//            key = "'getHub:' + #hubId"
//    )
    @Override
    public GetHubResponse getHub(UUID hubId) {
        return GetHubResponse.from(getHubForServer(hubId));
    }


    @Override
    public GetHubResponse getHubByRegion(String region) {
        Hub findHub = hubRepository.getHubByRegion(region)
                .orElseThrow(() -> new BusinessHubException(HubErrorCode.HUB_NOT_FOUND));

        return GetHubResponse.from(findHub);
    }

    //    @Cacheable(
//            cacheNames = "searchHub",
//            key = "'searchHub:' + (#searchHubRequest.name() ?: '') + ':' + (#searchHubRequest.region() ?: '')"
//    )
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
                updateHubRequest.address()
        );

        if (isAddressChanged(targetHub, sourceHub)) {
            getAndSetCoordinates(targetHub);
        }
        ;

        return UpdateHubResponse.from(targetHub.update(sourceHub));
    }

    private boolean isAddressChanged(Hub targetHub, Hub sourceHub) {
        return targetHub.getAddress().equals(sourceHub.getAddress());
    }

    @Override
    @Transactional
    public void deleteHub(UUID hubId) {
        Hub targetHub = getHubForServer(hubId);
        targetHub.delete();

        HubDeletedActivity hubDeletedActivity = getHubDeletedActivity(targetHub);
        try {
            kafkaTemplate.send("hub.deleted", objectMapper.writeValueAsString(hubDeletedActivity));
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e);
        }
    }

    private HubDeletedActivity getHubDeletedActivity(Hub targetHub) {
        return new HubDeletedActivity(targetHub.getHubId(), targetHub.getRegion(), targetHub.getDeletedAt());
    }

    /**
     * 서버에서만 사용하는 단일 조회 로직
     */
    private Hub getHubForServer(UUID hubId) {
        return hubRepository.getHub(hubId)
                .orElseThrow(() -> new BusinessHubException(HubErrorCode.HUB_NOT_FOUND));
    }

    private void getAndSetCoordinates(Hub hub) {
        GetCoordinatesResponse coordinates =
                geoLocationService.getCoordinates(GetCoordinatesRequest.from(hub.getAddress()));
        hub.setCoordinates(coordinates.addresses().get(0).latitude(), coordinates.addresses().get(0).longitude());
    }
}