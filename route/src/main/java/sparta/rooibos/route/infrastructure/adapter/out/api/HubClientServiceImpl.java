package sparta.rooibos.route.infrastructure.adapter.out.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.rooibos.route.application.dto.response.Hub.HubClientResponse;
import sparta.rooibos.route.application.port.out.HubClientService;
import sparta.rooibos.route.infrastructure.client.HubFeignClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubClientServiceImpl implements HubClientService {

    private final HubFeignClient hubFeignClient;

    @Override
    public HubClientResponse getHubByHubId(UUID hubId) {
        return hubFeignClient.getHubByHubId(hubId);
    }
}
