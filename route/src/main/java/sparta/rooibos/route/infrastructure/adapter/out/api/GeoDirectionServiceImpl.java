package sparta.rooibos.route.infrastructure.adapter.out.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.rooibos.route.application.dto.request.direction.GetGeoDirectionRequest;
import sparta.rooibos.route.application.dto.response.direction.GetGeoDirectionResponse;
import sparta.rooibos.route.application.port.out.GeoDirectionService;
import sparta.rooibos.route.infrastructure.client.NaverGeoDirectionClient;

@Service
@RequiredArgsConstructor
public class GeoDirectionServiceImpl implements GeoDirectionService {

    private final NaverGeoDirectionClient naverGeoDirectionClient;

    @Override
    public GetGeoDirectionResponse getGeoDirection(GetGeoDirectionRequest getGeoDirectionRequest) {
        return naverGeoDirectionClient.getDirection(getGeoDirectionRequest);
    }
}
