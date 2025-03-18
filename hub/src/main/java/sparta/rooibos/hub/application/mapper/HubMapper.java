package sparta.rooibos.hub.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sparta.rooibos.hub.application.dto.response.CreateHubResponseDto;
import sparta.rooibos.hub.application.dto.response.GetHubResponseDto;
import sparta.rooibos.hub.application.dto.response.UpdateHubResponseDto;
import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.application.dto.request.CreateHubRequestDto;
import sparta.rooibos.hub.application.dto.request.UpdateHubRequestDto;

@Mapper(componentModel = "spring")
public interface HubMapper {

    @Mapping(target = "hubId", ignore = true)
    @Mapping(target = "createAt", expression = "java(null)")
    @Mapping(target = "updateAt", expression = "java(null)")
    @Mapping(target = "createBy", expression = "java(null)")
    @Mapping(target = "updateBy", expression = "java(null)")
    @Mapping(target = "deleteAt", expression = "java(null)")
    @Mapping(target = "deleteBy", expression = "java(null)")
    Hub toHub(CreateHubRequestDto createHubRequestDto);

    @Mapping(target = "createAt", expression = "java(null)")
    @Mapping(target = "updateAt", expression = "java(null)")
    @Mapping(target = "createBy", expression = "java(null)")
    @Mapping(target = "updateBy", expression = "java(null)")
    @Mapping(target = "deleteAt", expression = "java(null)")
    @Mapping(target = "deleteBy", expression = "java(null)")
    Hub toHub(UpdateHubRequestDto updateHubRequestDto);

    CreateHubResponseDto toCreateHubResponseDto(Hub hub);

    GetHubResponseDto toGetHubResponseDto(Hub hub);

    UpdateHubResponseDto toUpdateHubResponseDto(Hub hub);
}