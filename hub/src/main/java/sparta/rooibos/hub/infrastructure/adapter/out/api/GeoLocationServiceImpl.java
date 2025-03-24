package sparta.rooibos.hub.infrastructure.adapter.out.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.rooibos.hub.application.dto.GeoLocation.request.GetCoordinatesRequest;
import sparta.rooibos.hub.application.dto.GeoLocation.response.GetCoordinatesResponse;
import sparta.rooibos.hub.application.port.out.GeoLocationService;
import sparta.rooibos.hub.infrastructure.client.NaverGeocodingClient;

@Service
@RequiredArgsConstructor
public class GeoLocationServiceImpl implements GeoLocationService {

    // TODO application 계층에 대한 의존을 제거하려면 공통 모듈을 두는 게 좋음

    private final NaverGeocodingClient naverGeocodingClient;

    @Override
    public GetCoordinatesResponse getCoordinates(GetCoordinatesRequest getCoordinatesRequest) {
        return naverGeocodingClient.getCoordinates(getCoordinatesRequest.address());
    }
}
