package com.sparta.rooibos.message.infrastuct.repository;

import com.sparta.rooibos.message.domain.entity.Message;
import com.sparta.rooibos.message.domain.repository.MessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMessageRepository extends JpaRepository<Message, UUID>, MessageRepository {
}
