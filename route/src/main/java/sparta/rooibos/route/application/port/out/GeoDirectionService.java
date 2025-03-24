package sparta.rooibos.route.application.port.out;

import sparta.rooibos.route.application.dto.request.direction.GetGeoDirectionRequest;
import sparta.rooibos.route.application.dto.response.direction.GetGeoDirectionResponse;

public interface GeoDirectionService {

    GetGeoDirectionResponse getGeoDirection(GetGeoDirectionRequest getGeoDirectionRequest);
}
