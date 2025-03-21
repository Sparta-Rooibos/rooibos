package com.sparta.rooibus.delivery.application.dto.request.feign.route;

import java.util.UUID;

public record GetRouteRequest(UUID departure, UUID arrival) {

    public static GetRouteRequest of(UUID departure, UUID arrival) {
        return new GetRouteRequest(departure, arrival);
    }
}
