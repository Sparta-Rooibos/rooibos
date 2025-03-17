package com.sparta.rooibos.client.application.dto.req;

import java.util.UUID;

public record UpdateHubIdRequest(UUID clientId, String hubId) {}
