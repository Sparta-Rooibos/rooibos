package com.sparta.rooibos.client.application.dto.req;

import java.util.UUID;

public record UpdateHubIdApplicationRequest(UUID clientId, String hubId) {
}
