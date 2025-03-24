package sparta.rooibos.hub.application.dto.message;

import java.time.LocalDateTime;
import java.util.UUID;

public record HubDeletedActivity(
        UUID hubId,
        String region,
        LocalDateTime deletedAt
) {}
