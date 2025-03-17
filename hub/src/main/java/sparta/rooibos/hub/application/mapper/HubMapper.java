package sparta.rooibos.hub.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sparta.rooibos.hub.application.dto.response.GetHubResponseDto;
import sparta.rooibos.hub.application.dto.response.UpdateHubResponseDto;
import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.application.dto.request.CreateHubRequestDto;
import sparta.rooibos.hub.application.dto.request.UpdateHubRequestDto;

@Mapper(componentModel = "spring")
public interface HubMapper {

    // TODO requestDto, responseDto 사용 확인하고, 이름 통일시키기
    @Mapping(target = "hubId", ignore = true)
    Hub toHub(CreateHubRequestDto createHubRequestDto);

    Hub toHub(UpdateHubRequestDto updateHubRequestDto);

    CreateHubRequestDto toCreateHubResponseDto(Hub hub);

    GetHubResponseDto toGetHubResponseDto(Hub hub);

    UpdateHubResponseDto toUpdateHubResponseDto(Hub hub);
}