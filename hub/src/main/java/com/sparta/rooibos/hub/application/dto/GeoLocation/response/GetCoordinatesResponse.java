package com.sparta.rooibos.hub.application.dto.GeoLocation.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GetCoordinatesResponse(
        List<Addresses> addresses
) {
    public record Addresses(
            @JsonProperty("x") String latitude,
            @JsonProperty("y") String longitude
    ) {}
}
