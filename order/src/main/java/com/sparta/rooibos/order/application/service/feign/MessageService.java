package com.sparta.rooibos.order.application.service.feign;

import com.sparta.rooibos.order.application.dto.request.CreateMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface MessageService {
    ResponseEntity<CreateMessageResponse> createMessage(
        String email, String username, String role,
        CreateMessageRequest request);
}
