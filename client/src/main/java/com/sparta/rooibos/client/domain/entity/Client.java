package com.sparta.rooibos.client.domain.entity;

import com.sparta.rooibos.client.domain.model.ClientType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "p_client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Client {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ClientType type;

    private String clientAddress;

    private String managedHubId;

    //생성
    public Client(String name, ClientType type, String managedHubId, String address) {
        this.name = name;
        this.type = type;
        this.managedHubId = managedHubId;
        this.clientAddress = address;
    }
}
