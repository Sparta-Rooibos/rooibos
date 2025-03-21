package sparta.rooibos.hub.application.service.port.out;

import sparta.rooibos.hub.application.dto.GeoLocation.request.GetCoordinatesRequest;
import sparta.rooibos.hub.application.dto.GeoLocation.response.GetCoordinatesResponse;

public interface GeoLocationService {

    GetCoordinatesResponse getCoordinates(GetCoordinatesRequest getCoordinatesRequest);
}
