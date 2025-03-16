package sparta.rooibos.hub.application.service.port;

import sparta.rooibos.hub.application.dto.HubResponseDto;
import sparta.rooibos.hub.presentation.dto.CreateHubRequestDto;
import sparta.rooibos.hub.presentation.dto.UpdateHubRequestDto;

import java.util.UUID;

public interface HubService {
    HubResponseDto createHub(CreateHubRequestDto createHubRequestDto);

    HubResponseDto getHub(UUID hubId);

    // TODO 동적 쿼리 로직 추가

    HubResponseDto updateHub(UpdateHubRequestDto updateHubRequestDto);
}
