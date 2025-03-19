package com.sparta.rooibos.message.application.dto.response;

import com.sparta.rooibos.message.domain.entity.Message;

import java.time.LocalDateTime;

public record UpdateMessageResponse(
        String id,
        String sender,
        String receiverId,
        String message,
        LocalDateTime sentAt,
        boolean status

) {
    public static UpdateMessageResponse update(Message message) {
        return new UpdateMessageResponse(
                message.getId().toString(),
                message.getSender(),
                message.getRecipient(),
                message.getText(),
                message.getSendingAt(),
                message.getStatus()


        );
    }
}
