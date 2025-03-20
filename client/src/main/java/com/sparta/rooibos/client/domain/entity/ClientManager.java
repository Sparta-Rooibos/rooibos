package com.sparta.rooibos.client.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class ClientManager {

    @Id
    private UUID clientId;
    private String userId;

    public static ClientManager create(UUID clientId, String userId) {
        return new ClientManager(clientId, userId);
    }

    private ClientManager(UUID clientId, String userId) {
        this.clientId = clientId;
        this.userId = userId;
    }
}
