package com.sparta.rooibos.message.application.service;

import com.sparta.rooibos.message.application.dto.request.CreateMessageRequest;
import com.sparta.rooibos.message.application.dto.request.SendMessageRequest;
import com.sparta.rooibos.message.application.dto.request.UpdateMessageRequest;
import com.sparta.rooibos.message.application.dto.response.*;
import com.sparta.rooibos.message.domain.entity.Message;
import com.sparta.rooibos.message.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;

    public CreateMessageResponse createMessage(CreateMessageRequest request) {
        return CreateMessageResponse.create(repository.save(request.toEntity()));
    }

    public SearchMessageResponse searchMessage() {
        return null;
    }

    public GetMessageResponse getMessage(UUID messageId) {
        Message message = repository.findById(messageId).orElseThrow(() -> new IllegalArgumentException("슬랙 메시지가 존재하지 않습니다."));
        return GetMessageResponse.get(message);
    }

    public UpdateMessageResponse updateMessage(UUID messageId, UpdateMessageRequest request) {
        Message message = repository.findById(messageId).orElseThrow(() -> new IllegalArgumentException("슬랙 메시지가 존재하지 않습니다."));
        message.changeMessage(request.message());
        return UpdateMessageResponse.update(message);
    }

    public void deleteMessage(UUID messageId) {
        Message message = repository.findById(messageId).orElseThrow(() -> new IllegalArgumentException("슬랙 메시지가 존재하지 않습니다."));
        message.delete("계정 아이디");
    }

    public SendMessageResponse sendMessage(SendMessageRequest request) {
        return null;
    }
}
