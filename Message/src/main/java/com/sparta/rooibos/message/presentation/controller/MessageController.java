package com.sparta.rooibos.message.presentation.controller;

import com.sparta.rooibos.message.application.annotation.RoleCheck;
import com.sparta.rooibos.message.application.dto.request.CreateMessageRequest;
import com.sparta.rooibos.message.application.dto.request.SearchMessageRequest;
import com.sparta.rooibos.message.application.dto.response.*;
import com.sparta.rooibos.message.application.service.MessageService;
import com.sparta.rooibos.message.application.type.Role;
import jakarta.validation.Valid;
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
    @RoleCheck({Role.MASTER, Role.HUB, Role.DELIVERY, Role.CLIENT})
    public ResponseEntity<CreateMessageResponse> createMessage(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @RequestBody @Valid CreateMessageRequest request) {
        return ResponseEntity.ok(messageService.createMessage(email, request));
    }
    @GetMapping
    @RoleCheck({Role.MASTER})
    public ResponseEntity<SearchMessageResponse> searchMessage(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @ModelAttribute SearchMessageRequest request) {
        return ResponseEntity.ok(messageService.searchMessage(request));
    }
    @GetMapping("/{messageId}")
    @RoleCheck({Role.MASTER})
    public ResponseEntity<GetMessageResponse> getMessage(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID messageId) {
        return ResponseEntity.ok(messageService.getMessage(messageId));
    }

    @PatchMapping("/{messageId}")
    @RoleCheck({Role.MASTER})
    public ResponseEntity<Void> deleteMessage(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID messageId) {
        messageService.deleteMessage(email, messageId);
        return ResponseEntity.noContent().build();
    }

}
