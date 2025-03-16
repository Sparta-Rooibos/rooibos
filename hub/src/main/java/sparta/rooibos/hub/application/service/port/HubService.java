package sparta.rooibos.hub.application.service.port;

import sparta.rooibos.hub.application.dto.CreateHubResponseDto;
import sparta.rooibos.hub.presentation.dto.CreateHubRequestDto;

public interface HubService {

    CreateHubResponseDto createHub(CreateHubRequestDto createHubRequestDto);
}
