package sparta.rooibos.hub.application.dto.hub.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CreateHubRequest(
        @NotEmpty String name,
        @NotEmpty @NotBlank String region,
        @NotEmpty String address
) {}
