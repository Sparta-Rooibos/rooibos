package sparta.rooibos.hub.application.service.port;

import sparta.rooibos.hub.application.dto.response.GetHubResponseDto;
import sparta.rooibos.hub.application.dto.response.UpdateHubResponseDto;
import sparta.rooibos.hub.application.dto.request.CreateHubRequestDto;
import sparta.rooibos.hub.application.dto.request.UpdateHubRequestDto;

import java.util.UUID;

public interface HubService {
    CreateHubRequestDto createHub(CreateHubRequestDto createHubRequestDto);

    GetHubResponseDto getHub(UUID hubId);

    // TODO 동적 쿼리 로직 추가

    UpdateHubResponseDto updateHub(UpdateHubRequestDto updateHubRequestDto);
}
