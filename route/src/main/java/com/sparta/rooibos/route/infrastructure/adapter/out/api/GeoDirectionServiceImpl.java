package com.sparta.rooibos.route.infrastructure.adapter.out.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sparta.rooibos.route.application.dto.response.direction.GetGeoDirectionResponse;
import com.sparta.rooibos.route.application.port.out.GeoDirectionService;
import com.sparta.rooibos.route.infrastructure.client.NaverGeoDirectionClient;

@Service
@RequiredArgsConstructor
public class GeoDirectionServiceImpl implements GeoDirectionService {

    private final NaverGeoDirectionClient naverGeoDirectionClient;

    @Override
    public GetGeoDirectionResponse getGeoDirection(String start, String goal) {
        return naverGeoDirectionClient.getDirection(start, goal);
    }
}
