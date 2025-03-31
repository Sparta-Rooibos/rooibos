package com.sparta.rooibos.message.application.service;


import com.sparta.rooibos.message.application.ai.AiService;
import com.sparta.rooibos.message.application.custom.MessageErrorCode;
import com.sparta.rooibos.message.application.dto.request.CreateMessageRequest;
import com.sparta.rooibos.message.application.dto.request.SearchMessageRequest;
import com.sparta.rooibos.message.application.dto.response.CreateMessageResponse;
import com.sparta.rooibos.message.application.dto.response.GetMessageResponse;
import com.sparta.rooibos.message.application.dto.response.SearchMessageResponse;
import com.sparta.rooibos.message.application.exception.BusinessMessageException;
import com.sparta.rooibos.message.application.slack.SlackService;
import com.sparta.rooibos.message.domain.entity.Message;
import com.sparta.rooibos.message.domain.model.Pagination;
import com.sparta.rooibos.message.domain.repository.MessageRepository;
import com.sparta.rooibos.message.domain.repository.MessageRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository repository;
    private final MessageRepositoryCustom repositoryCustom;

    private final SlackService slackService;
    private final AiService aiService;

    public CreateMessageResponse createMessage(String email, CreateMessageRequest request) {
        try {
            slackService.sendMessage(aiService.aiMadeMessage(request.content()));
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return CreateMessageResponse.create(repository.save(request.toEntity(email)));
    }



    public SearchMessageResponse searchMessage(SearchMessageRequest request) {
        Pagination<Message> messages = repositoryCustom.searchMessages(request.toCriteria());
        return SearchMessageResponse.from(messages);
    }

    public GetMessageResponse getMessage(UUID messageId) {
        Message message = repository.findById(messageId).orElseThrow(() -> new BusinessMessageException(MessageErrorCode.MESSAGE_NOT_FOUND));
        return GetMessageResponse.get(message);
    }


    public void deleteMessage(String email, UUID messageId) {
        Message message = repository.findById(messageId).orElseThrow(() -> new BusinessMessageException(MessageErrorCode.MESSAGE_NOT_FOUND));
        message.delete(email);
    }

}
