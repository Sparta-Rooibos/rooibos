package com.sparta.rooibos.message.application.dto.response;


import com.sparta.rooibos.message.domain.entity.Message;
import com.sparta.rooibos.message.domain.model.Pagination;

import java.util.List;

public record SearchMessageResponse(
        List<SearchMessageListResponse> messages,

        long totalCount,
        long page,
        long size) {


    public static SearchMessageResponse from(Pagination<Message> messages) {
        return new SearchMessageResponse(
        messages.getContent().stream().map(SearchMessageListResponse::new).toList(),
        messages.getTotal(),
        messages.getPage(),
        messages.getSize()
        );
    }
}
