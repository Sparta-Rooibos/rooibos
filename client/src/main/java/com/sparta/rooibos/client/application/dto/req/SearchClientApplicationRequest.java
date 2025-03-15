package com.sparta.rooibos.client.application.dto.req;

import org.springframework.data.domain.Pageable;

public record SearchClientApplicationRequest(String name, String address, String type, Boolean isDeleted, Pageable pageable) {}
