package com.sparta.rooibos.message.application.dto.response;


import com.sparta.rooibos.message.domain.entity.Message;

import java.time.LocalDateTime;

public record GetMessageResponse(
        String id,
        String sender,
        String receiverId,
        String message,
        LocalDateTime sendAt,
        boolean status
) {
    public static GetMessageResponse get(Message message) {
        return new GetMessageResponse(
                message.getId().toString(),
                message.getSender(),
                message.getRecipient(),
                message.getText(),
                message.getSendingAt(),
                message.getStatus()

        );
    }
}
