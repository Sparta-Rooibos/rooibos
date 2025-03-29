package com.sparta.rooibos.order.application.dto.request;

public record CreateMessageRequest(
    String recipient,
    String sender,
    String content
) {
    public static CreateMessageRequest of(String recipient, String sender, String content) {
        return new CreateMessageRequest(recipient, sender, content);
    }
}
