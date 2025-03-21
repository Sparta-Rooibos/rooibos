package com.sparta.rooibos.client.application.dto.response;

import java.util.List;

public record SearchClientResponse(List<SearchClientListResponse> clients, long totalCount,
                                   long page, long size) {}
