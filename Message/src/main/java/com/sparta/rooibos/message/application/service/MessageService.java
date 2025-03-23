package com.sparta.rooibos.message.application.service;

import com.sparta.rooibos.message.application.custom.MessageErrorCode;
import com.sparta.rooibos.message.application.dto.request.CreateMessageRequest;
import com.sparta.rooibos.message.application.dto.request.SearchMessageRequest;
import com.sparta.rooibos.message.application.dto.response.*;
import com.sparta.rooibos.message.application.exception.BusinessMessageException;
import com.sparta.rooibos.message.domain.entity.Message;
import com.sparta.rooibos.message.domain.model.Pagination;
import com.sparta.rooibos.message.domain.repository.MessageRepository;
import com.sparta.rooibos.message.domain.repository.MessageRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository repository;
    private final MessageRepositoryCustom repositoryCustom;

    @Value("${slack.token}")
    private String slackToken;

    public CreateMessageResponse createMessage(String email, CreateMessageRequest request) {
        sendMessage(request.content());
        return CreateMessageResponse.create(repository.save(request.toEntity(email)));
    }

    private void sendMessage(String content) {
        WebClient webClient = WebClient.create("https://slack.com/api");

        Disposable subscribe = webClient.post()
                .uri("/chat.postMessage")
                .header("Authorization", "Bearer " + slackToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                        "channel": "#테스트입니다",
                        "text": "%s"
                        }
                        """.formatted(content)).retrieve()
                .bodyToMono(String.class)
                .subscribe();

        System.out.println(subscribe.isDisposed());

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
