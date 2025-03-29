package com.sparta.rooibos.hub.application.port.out;

import com.sparta.rooibos.hub.application.dto.GeoLocation.request.GetCoordinatesRequest;
import com.sparta.rooibos.hub.application.dto.GeoLocation.response.GetCoordinatesResponse;

public interface GeoLocationService {
    GetCoordinatesResponse getCoordinates(GetCoordinatesRequest getCoordinatesRequest);
}

