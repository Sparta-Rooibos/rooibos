package com.sparta.rooibos.client.application.dto.res;

import java.util.List;

public record SearchClientApplicationResponse(List<SearchClientApplicationListResponse> clients, long totalCount,
                                              long page, long size) {}
