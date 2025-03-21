package com.sparta.rooibos.message.application.dto.request;

import com.sparta.rooibos.message.domain.entity.Message;

public record CreateMessageRequest(
        String recipient,
        String sender,
        String content
) {
    public Message toEntity() {
        return Message.create(
                content,
                sender,
                recipient
        );
    }
}
