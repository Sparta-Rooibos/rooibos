package com.sparta.rooibos.message.application.dto.response;


import com.sparta.rooibos.message.domain.entity.Message;

import java.time.LocalDateTime;

public record CreateMessageResponse(
        String id,
        String sender,
        String receiver,
        String text,
        LocalDateTime sendAt,
        boolean status
) {
    public static CreateMessageResponse create(Message message) {
        return new CreateMessageResponse(
                message.getId().toString(),
                message.getSender(),
                message.getRecipient(),
                message.getText(),
                message.getSendingAt(),
                message.getStatus()
        );
    }
}
