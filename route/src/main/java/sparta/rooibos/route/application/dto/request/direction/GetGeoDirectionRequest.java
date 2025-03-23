package sparta.rooibos.route.application.dto.request.direction;

public record GetGeoDirectionRequest(
        String start,
        String goal
) {
    public static GetGeoDirectionRequest of(String start, String goal) {
        return new GetGeoDirectionRequest(start, goal);
    }
}
