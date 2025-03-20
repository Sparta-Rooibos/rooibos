package com.sparta.rooibos.message.presentation.controller;

import com.sparta.rooibos.message.application.dto.request.CreateMessageRequest;
import com.sparta.rooibos.message.application.dto.request.SearchMessageRequest;
import com.sparta.rooibos.message.application.dto.response.*;
import com.sparta.rooibos.message.application.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public CreateMessageResponse createMessage(@RequestBody CreateMessageRequest request) {
        return messageService.createMessage(request);
    }
    @GetMapping
    public SearchMessageResponse searchMessage(@ModelAttribute SearchMessageRequest request) {
        return messageService.searchMessage(request);
    }
    @GetMapping("/{messageId}")
    public GetMessageResponse getMessage(@PathVariable UUID messageId) {
        return messageService.getMessage(messageId);
    }

    @PatchMapping("/{messageId}")
    public void deleteMessage(@PathVariable UUID messageId) {
        messageService.deleteMessage(messageId);
    }

}
