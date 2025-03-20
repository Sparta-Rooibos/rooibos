package com.sparta.rooibos.message.presentation.controller;

import com.sparta.rooibos.message.application.dto.request.CreateMessageRequest;
import com.sparta.rooibos.message.application.dto.request.SearchMessageRequest;
import com.sparta.rooibos.message.application.dto.response.*;
import com.sparta.rooibos.message.application.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<CreateMessageResponse> createMessage(@RequestBody CreateMessageRequest request) {
        return ResponseEntity.ok(messageService.createMessage(request));
    }
    @GetMapping
    public ResponseEntity<SearchMessageResponse> searchMessage(@ModelAttribute SearchMessageRequest request) {
        return ResponseEntity.ok(messageService.searchMessage(request));
    }
    @GetMapping("/{messageId}")
    public ResponseEntity<GetMessageResponse> getMessage(@PathVariable UUID messageId) {
        return ResponseEntity.ok(messageService.getMessage(messageId));
    }

    @PatchMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }

}
