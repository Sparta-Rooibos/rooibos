package com.spring.cloud.client.auth.infrastructure.kafka;

import com.spring.cloud.client.auth.application.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaResponseHandler {
    private final Map<String, CompletableFuture<UserDTO>> pendingRequests = new ConcurrentHashMap<>();

    public CompletableFuture<UserDTO> createRequest(String requestId) {
        CompletableFuture<UserDTO> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);
        return future;
    }

    public void completeRequest(String requestId, UserDTO userDTO) {
        CompletableFuture<UserDTO> future = pendingRequests.remove(requestId);
        if (future != null) {
            future.complete(userDTO);
        }
    }
}