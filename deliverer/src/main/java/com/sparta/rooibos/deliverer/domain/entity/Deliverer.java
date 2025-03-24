package com.sparta.rooibos.deliverer.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_deliverer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Deliverer extends BaseEntity {
    @Id
    @Column(name = "deliverer_id", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(name = "slack_account", nullable = false, unique = true)
    private String slackAccount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DelivererType type;

    @Column(name = "hub_id", columnDefinition = "UUID")
    private UUID hubId;

    @Column(unique = true)
    private int order;

    private boolean hidden;

    public static Deliverer create(UUID id, String username, String email, String slackAccount, String phone, DelivererType type, UUID hubId, int order) {
        return new Deliverer(id, username, email, slackAccount, phone, type, hubId, order, false);
    }

    public void update(DelivererType type, UUID hubId) {
        this.type = type;
        this.hubId = hubId;
    }

    public void delete(String deletedBy) {
        this.hidden = true;
        this.markAsDeleted(deletedBy);
    }
}