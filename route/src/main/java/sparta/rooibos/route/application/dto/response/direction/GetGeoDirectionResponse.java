package sparta.rooibos.route.application.dto.response.direction;

import java.util.List;

public record GetGeoDirectionResponse(
        Route route
) {
    public Integer getDistance() {
        return route.traoptimalList.get(0).summary.distance;
    }

    public Integer getDuration() {
        return route.traoptimalList.get(0).summary.duration;
    }

    public record Route(
            List<Traoptimal> traoptimalList
    ) {
        public record Traoptimal(
                Summary summary
        ) {
            public record Summary(
                    Integer distance,
                    Integer duration
            ) {}
        }
    }
}
