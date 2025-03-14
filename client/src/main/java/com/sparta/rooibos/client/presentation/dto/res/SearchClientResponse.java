package com.sparta.rooibos.client.presentation.dto.res;

import com.sparta.rooibos.client.application.dto.res.SearchClientApplicationResponse;

import java.util.List;

public record SearchClientResponse(List<SearchClientListResponse> clients, long totalCount, long page, long size) {
    public SearchClientResponse(SearchClientApplicationResponse response) {
        this(response.clients()
                .stream().map(r -> new SearchClientListResponse(r)).toList(), response.totalCount(), response.page(), response.size());
    }
}
