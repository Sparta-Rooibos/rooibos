package com.sparta.rooibus.order.application.service.feign;

import com.sparta.rooibus.order.application.dto.request.CreateMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface MessageService {
    ResponseEntity<CreateMessageResponse> createMessage(
        String email, String username, String role,
        CreateMessageRequest request);
}
