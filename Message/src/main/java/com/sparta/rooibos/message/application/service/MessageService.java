package com.sparta.rooibos.message.application.service;

import com.sparta.rooibos.message.application.dto.request.CreateMessageRequest;
import com.sparta.rooibos.message.application.dto.request.SearchMessageRequest;
import com.sparta.rooibos.message.application.dto.response.*;
import com.sparta.rooibos.message.domain.entity.Message;
import com.sparta.rooibos.message.domain.model.Pagination;
import com.sparta.rooibos.message.domain.repository.MessageRepository;
import com.sparta.rooibos.message.domain.repository.MessageRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository repository;
    private final MessageRepositoryCustom repositoryCustom;

    public CreateMessageResponse createMessage(CreateMessageRequest request) {
        sendMessage(request.content());
        return CreateMessageResponse.create(repository.save(request.toEntity()));
    }

    private void sendMessage(String content) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://hooks.slack.com/services/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")
                .build();

        String payload = """
                {"text": %s}
                """.formatted(content);

        Mono<String> response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class);


        response.subscribe(message -> log.info("Message sent: {}", message));
    }

    public SearchMessageResponse searchMessage(SearchMessageRequest request) {
        Pagination<Message> messages = repositoryCustom.searchMessages(request.toCriteria());
        return SearchMessageResponse.from(messages);
    }

    public GetMessageResponse getMessage(UUID messageId) {
        Message message = repository.findById(messageId).orElseThrow(() -> new IllegalArgumentException("슬랙 메시지가 존재하지 않습니다."));
        return GetMessageResponse.get(message);
    }


    public void deleteMessage(UUID messageId) {
        Message message = repository.findById(messageId).orElseThrow(() -> new IllegalArgumentException("슬랙 메시지가 존재하지 않습니다."));
        message.delete("계정 아이디");
    }

}
