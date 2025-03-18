package sparta.rooibos.hub.application.service.port;

import sparta.rooibos.hub.application.dto.request.CreateHubRequestDto;
import sparta.rooibos.hub.application.dto.request.UpdateHubRequestDto;
import sparta.rooibos.hub.application.dto.response.CreateHubResponseDto;
import sparta.rooibos.hub.application.dto.response.GetHubResponseDto;
import sparta.rooibos.hub.application.dto.response.SearchHubResponseDto;
import sparta.rooibos.hub.application.dto.response.UpdateHubResponseDto;

import java.util.UUID;

public interface HubService {
    CreateHubResponseDto createHub(CreateHubRequestDto createHubRequestDto);

    GetHubResponseDto getHub(UUID hubId);

    SearchHubResponseDto searchHub(String name, String region, int page, int size);

    UpdateHubResponseDto updateHub(UpdateHubRequestDto updateHubRequestDto);

    void deleteHub(UUID hubId);
}
