package com.sparta.rooibos.client.application.dto.res;

import java.util.List;

public record SearchClientResponse(List<SearchClientListResponse> clients, long totalCount,
                                   long page, long size) {}
