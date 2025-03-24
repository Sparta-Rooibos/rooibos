package sparta.rooibos.hub.application.dto.GeoLocation.request;

import jakarta.validation.constraints.NotEmpty;

public record GetCoordinatesRequest(
        @NotEmpty String address
) {
    public static GetCoordinatesRequest from(String address) {
        return new GetCoordinatesRequest(address);
    }
}
