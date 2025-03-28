package com.sparta.rooibos.route.application.port.out;

import com.sparta.rooibos.route.application.dto.response.direction.GetGeoDirectionResponse;

public interface GeoDirectionService {

    GetGeoDirectionResponse getGeoDirection(String start, String goal);
}
