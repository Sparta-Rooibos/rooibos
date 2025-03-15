package com.sparta.rooibos.client.application.dto.req;

import java.util.UUID;

public record UpdateClientApplicationRequest(UUID clientId, String name, String address) {}
