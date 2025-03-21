package com.sparta.rooibus.delivery.application.dto.response.feign.route;

import java.util.List;
import java.util.UUID;

public record GetRouteResponse(
    List<UUID> routeList,
    String expected_distance,
    String expected_time
    ) {

}
