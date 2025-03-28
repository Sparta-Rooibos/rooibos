package com.sparta.rooibos.hub.application.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sparta.rooibos.hub.application.dto.GeoLocation.request.GetCoordinatesRequest;
import com.sparta.rooibos.hub.application.dto.GeoLocation.response.GetCoordinatesResponse;
import com.sparta.rooibos.hub.application.dto.hub.request.CreateHubRequest;
import com.sparta.rooibos.hub.application.dto.hub.request.SearchHubRequest;
import com.sparta.rooibos.hub.application.dto.hub.request.UpdateHubRequest;
import com.sparta.rooibos.hub.application.dto.hub.response.CreateHubResponse;
import com.sparta.rooibos.hub.application.dto.hub.response.GetHubResponse;
import com.sparta.rooibos.hub.application.dto.hub.response.SearchHubResponse;
import com.sparta.rooibos.hub.application.dto.hub.response.UpdateHubResponse;
import com.sparta.rooibos.hub.application.dto.message.HubDeletedActivity;
import com.sparta.rooibos.hub.application.exception.BusinessHubException;
import com.sparta.rooibos.hub.application.exception.custom.HubErrorCode;
import com.sparta.rooibos.hub.application.port.in.HubService;
import com.sparta.rooibos.hub.application.port.out.GeoLocationService;
import com.sparta.rooibos.hub.domain.model.Hub;
import com.sparta.rooibos.hub.domain.model.Pagination;
import com.sparta.rooibos.hub.domain.respository.HubRepository;

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

    @Override
    public GetHubResponse getHub(UUID hubId) {
        return GetHubResponse.from(getHubForServer(hubId));
    }

    @Override
    public boolean isExistingHub(UUID hubId) {
        return hubRepository.getHub(hubId).isPresent();
    }

    @Override
    public GetHubResponse getHubByRegion(String region) {
        Hub findHub = hubRepository.getHubByRegion(region)
                .orElseThrow(() -> new BusinessHubException(HubErrorCode.HUB_NOT_FOUND));

        return GetHubResponse.from(findHub);
    }

//    @Cacheable(
//            cacheNames = "searchHub",
//            key = "'searchHub:' + #email + (#searchHubRequest.name() ?: '') + ':' + (#searchHubRequest.region() ?: '')"
//    )
    @Override
    public SearchHubResponse searchHub(String email, SearchHubRequest searchHubRequest) {
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