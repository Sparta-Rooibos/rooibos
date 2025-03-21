package com.sparta.rooibos.message.application.dto.request;

import com.sparta.rooibos.message.domain.critria.MessageCriteria;

import java.util.UUID;



public record SearchMessageRequest(UUID id, String sender, String receiverId,
                                   String text,
                                   Integer page, Integer size, String sort) {


    public SearchMessageRequest {
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        sort = sort == null ? "createdAt" : sort;
        // 기간에 따라 조회하도록 변경
//        startTime = (startTime == null && endTime != null) ? LocalDateTime.now() : null;
    }

    public MessageCriteria toCriteria() {
        return new MessageCriteria(
                id,
                sender,
                receiverId,
                text,
                page,
                size,
                sort
        );
    }
}
