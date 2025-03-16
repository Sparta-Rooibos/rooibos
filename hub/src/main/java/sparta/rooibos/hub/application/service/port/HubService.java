package sparta.rooibos.hub.application.service.port;

import sparta.rooibos.hub.application.dto.HubResponseDto;
import sparta.rooibos.hub.presentation.dto.CreateHubRequestDto;

import java.util.UUID;

public interface HubService {
    HubResponseDto createHub(CreateHubRequestDto createHubRequestDto);

    HubResponseDto getHub(UUID hubId);
}
