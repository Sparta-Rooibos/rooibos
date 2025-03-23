package com.sparta.rooibos.message.domain.critria;

import java.util.UUID;

public record MessageCriteria(UUID id, String sender, String receiverId,
                              String text,
                              Integer page, Integer size, String sort) {
}
