package com.sparta.rooibos.message.application.dto.request;

import com.sparta.rooibos.message.domain.entity.Message;
import jakarta.validation.constraints.NotNull;

public record CreateMessageRequest(
        @NotNull String recipient,
        @NotNull String sender,
        @NotNull String content
) {
    public Message toEntity() {
        return Message.create(
                content,
                sender,
                recipient
        );
    }
}
