package sparta.rooibos.hub.application.service.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.rooibos.hub.application.dto.CreateHubResponseDto;
import sparta.rooibos.hub.application.mapper.HubMapper;
import sparta.rooibos.hub.application.service.port.HubService;
import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.respository.HubRepository;
import sparta.rooibos.hub.presentation.dto.CreateHubRequestDto;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

    private final HubMapper hubMapper;
    private final HubRepository hubRepository;

    @Override
    @Transactional
    public CreateHubResponseDto createHub(CreateHubRequestDto createHubRequestDto) {
        Hub newHub = hubMapper.toHub(createHubRequestDto);

        return hubMapper.toCreateHubResponseDto(hubRepository.createHub(newHub));
    }
}
