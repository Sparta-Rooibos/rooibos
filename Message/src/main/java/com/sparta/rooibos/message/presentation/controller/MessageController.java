package com.sparta.rooibos.message.presentation.controller;

import com.sparta.rooibos.message.application.dto.request.CreateMessageRequest;
import com.sparta.rooibos.message.application.dto.request.SendMessageRequest;
import com.sparta.rooibos.message.application.dto.request.UpdateMessageRequest;
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
    public SearchMessageResponse searchMessage() {
        return messageService.searchMessage();
    }
    @GetMapping("/{messageId}")
    public GetMessageResponse getMessage(@PathVariable UUID messageId) {
        return messageService.getMessage(messageId);
    }
    @PutMapping("/{messageId}")
    public UpdateMessageResponse updateMessage(@PathVariable UUID messageId, @RequestBody UpdateMessageRequest request) {
       return messageService.updateMessage(messageId,request);
    }
    @PatchMapping("/{messageId}")
    public void deleteMessage(@PathVariable UUID messageId) {
        messageService.deleteMessage(messageId);
    }

    @PostMapping
    public SendMessageResponse sendMessage(@RequestBody SendMessageRequest request) {
       return messageService.sendMessage(request);
    }
}
