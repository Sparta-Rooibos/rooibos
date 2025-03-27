package com.sparta.rooibos.client.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Client {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ClientType type;

    private String clientAddress;

    private String managedHubId;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createAt;

    @CreatedBy
    @Column(updatable = false, nullable = false)
    private String createBy;

    @UpdateTimestamp
    @Column
    private LocalDateTime updateAt;

    @LastModifiedBy
    @Column
    private String updateBy;

    @Column
    private LocalDateTime deleteAt;

    @Column
    private String deleteBy;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Client> clientManagers;
    //생성
    public Client(String name, ClientType type, String managedHubId, String address, String username) {
        this.name = name;
        this.type = type;
        this.managedHubId = managedHubId;
        this.clientAddress = address;
        this.createBy = username;
    }

    public Client(UUID clientId) {
        this.id = clientId;
    }

    public void update(String name, String address, String username) {
        this.name = name;
        this.clientAddress = address;
        this.updateBy = username;
    }

    public boolean delete(String userName) {
        this.deleteAt = LocalDateTime.now();
        this.deleteBy = userName;
        return true;
    }

    public boolean changeUsedHub(String hubId) {
        this.managedHubId = hubId;
        return true;
    }
}
