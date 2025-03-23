package com.sparta.rooibos.message.application.dto.request;

import com.sparta.rooibos.message.domain.entity.Message;
import jakarta.validation.constraints.NotNull;

public record CreateMessageRequest(
        @NotNull String recipient,
        @NotNull String sender,
        @NotNull String content
) {
    public Message toEntity(String message) {
        return Message.create(
                message,
                content,
                sender,
                recipient
        );
    }
}
