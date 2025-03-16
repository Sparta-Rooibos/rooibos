package sparta.rooibos.hub.application.service.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.rooibos.hub.application.dto.HubResponseDto;
import sparta.rooibos.hub.application.mapper.HubMapper;
import sparta.rooibos.hub.application.service.port.HubService;
import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.respository.HubRepository;
import sparta.rooibos.hub.presentation.dto.CreateHubRequestDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

    private final HubMapper hubMapper;
    private final HubRepository hubRepository;

    @Override
    @Transactional
    public HubResponseDto createHub(CreateHubRequestDto createHubRequestDto) {
        Hub newHub = hubMapper.toHub(createHubRequestDto);

        return hubMapper.toHubResponseDto(hubRepository.createHub(newHub));
    }

    @Override
    public HubResponseDto getHub(UUID hubId) {
        return hubMapper.toHubResponseDto(getHubForServer(hubId));
    }

    /**
     * 서버에서만 사용하는 단일 조회 로직
     */
    private Hub getHubForServer(UUID hubId) {
        return hubRepository.getHub(hubId)
                // TODO 커스텀 예외 추가
                .orElseThrow(() -> new IllegalArgumentException("Hub not found"));
    }
}
