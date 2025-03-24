package sparta.rooibos.route.application.dto.response.Hub;

import java.util.UUID;

public record HubClientResponse(
        UUID hubId,
        String latitude,
        String longitude
) {
    public String getCoordinates() {
        return String.format("%s,%s", latitude, longitude);
    }
}
