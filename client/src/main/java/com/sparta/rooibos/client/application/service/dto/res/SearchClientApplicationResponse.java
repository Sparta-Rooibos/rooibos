package com.sparta.rooibos.client.application.service.dto.res;

import com.sparta.rooibos.client.presentation.dto.res.SearchClientListResponse;

import java.util.List;

public record SearchClientApplicationResponse(List<SearchClientApplicationListResponse> clients, long totalCount, long page, long size) {

}
