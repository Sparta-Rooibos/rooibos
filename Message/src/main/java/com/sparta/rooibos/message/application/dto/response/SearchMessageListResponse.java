package com.sparta.rooibos.message.application.dto.response;

import com.sparta.rooibos.message.domain.entity.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public record SearchMessageListResponse(
        UUID id,
        String sender,
        String receiverId,
        String text,
        LocalDateTime sendAt
) {
    public SearchMessageListResponse(Message message) {
        this(message.getId(),
            message.getSender(),
            message.getRecipient(),
            message.getText(),
            message.getSendingAt());
    }
}
