package com.sparta.rooibos.client.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "p_client_manager")
public class ClientManager {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public static ClientManager create(UUID clientId, String userId) {
        return new ClientManager(clientId, userId);
    }

    private ClientManager(UUID clientId, String userId) {
        this.client = new Client(clientId);
        this.userId = userId;
    }
}
