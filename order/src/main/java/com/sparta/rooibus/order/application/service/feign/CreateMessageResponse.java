package com.sparta.rooibus.order.application.service.feign;

import java.time.LocalDateTime;

public record CreateMessageResponse(
    String id,
    String sender,
    String receiver,
    String text,
    LocalDateTime sendAt,
    boolean status
) {

}
