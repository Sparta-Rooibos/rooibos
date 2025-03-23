package com.sparta.rooibos.message.domain.repository;

import com.sparta.rooibos.message.domain.entity.Message;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {
    Message save(Message message);
    Optional<Message> findById(UUID id);
}
