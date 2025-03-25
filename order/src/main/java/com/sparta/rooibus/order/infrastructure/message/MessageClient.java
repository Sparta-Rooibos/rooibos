package com.sparta.rooibus.order.infrastructure.message;

import com.sparta.rooibus.order.application.dto.request.CreateMessageRequest;
import com.sparta.rooibus.order.application.service.feign.CreateMessageResponse;
import com.sparta.rooibus.order.application.service.feign.MessageService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Primary
@FeignClient(name = "message-service", url = "http://localhost:19099")
public interface MessageClient extends MessageService {
    @PostMapping("/api/v1/message")
    ResponseEntity<CreateMessageResponse> createMessage(
        @RequestHeader("X-User-Email") String email,
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @RequestBody CreateMessageRequest request);
}
