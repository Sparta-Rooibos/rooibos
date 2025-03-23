package sparta.rooibos.route.application.dto.request.direction;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetGeoDirectionRequest(
        @JsonProperty("start") String start,
        @JsonProperty("goal") String goal
) {
    public static GetGeoDirectionRequest of(String start, String goal) {
        return new GetGeoDirectionRequest(start, goal);
    }
}
