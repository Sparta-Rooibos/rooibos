package sparta.rooibos.route.application.dto.response.direction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public record GetGeoDirectionResponse(
        @JsonProperty("code") int code,
        @JsonProperty("message") String message,
        @JsonProperty("currentDateTime") String currentDateTime,
        @JsonProperty("route") Route route
) {
    public Integer getDistance() {

        return route().traoptimal().get(0).summary().distance(); // 미터
    }

    public Integer getDuration() {
        return route().traoptimal().get(0).summary().duration() / 1000; // 밀리초
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Route(
            List<Traoptimal> traoptimal
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Traoptimal(
                @JsonProperty("summary") Summary summary
        ) {
            @JsonIgnoreProperties(ignoreUnknown = true)
            public record Summary(
                    @JsonProperty("distance") Integer distance,
                    @JsonProperty("duration") Integer duration
            ) {
            }
        }
    }
}
