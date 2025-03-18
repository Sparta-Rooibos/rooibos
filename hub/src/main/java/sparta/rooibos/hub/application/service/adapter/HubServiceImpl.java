package sparta.rooibos.hub.application.service.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.rooibos.hub.application.dto.request.CreateHubRequestDto;
import sparta.rooibos.hub.application.dto.request.UpdateHubRequestDto;
import sparta.rooibos.hub.application.dto.response.CreateHubResponseDto;
import sparta.rooibos.hub.application.dto.response.GetHubResponseDto;
import sparta.rooibos.hub.application.dto.response.SearchHubResponseDto;
import sparta.rooibos.hub.application.dto.response.UpdateHubResponseDto;
import sparta.rooibos.hub.application.mapper.HubMapper;
import sparta.rooibos.hub.application.service.port.HubService;
import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.model.Pagination;
import sparta.rooibos.hub.domain.respository.HubRepository;

import java.util.UUID;

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

    @Override
    public GetHubResponseDto getHub(UUID hubId) {
        return hubMapper.toGetHubResponseDto(getHubForServer(hubId));
    }

    @Override
    public SearchHubResponseDto searchHub(String name, String region, int page, int size) {
        Pagination<Hub> hubPagination = hubRepository.searchHub(name, region, page, size);

        return SearchHubResponseDto.of(
                hubPagination.getPage(),
                hubPagination.getSize(),
                hubPagination.getTotal(),
                hubPagination.getContent()
        );
    }

    @Override
    @Transactional
    public UpdateHubResponseDto updateHub(UpdateHubRequestDto updateHubRequestDto) {
        Hub targetHub = getHubForServer(updateHubRequestDto.hubId());
        Hub sourceHub = hubMapper.toHub(updateHubRequestDto);

        return hubMapper.toUpdateHubResponseDto(targetHub.update(sourceHub));
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
                // TODO 커스텀 예외 추가
                .orElseThrow(() -> new IllegalArgumentException("Hub not found"));
    }
}
