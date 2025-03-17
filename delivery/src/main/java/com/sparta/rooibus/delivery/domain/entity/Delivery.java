package com.sparta.rooibus.delivery.domain.entity;

import com.sparta.rooibus.delivery.domain.model.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "p_delivery")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor
public class Delivery {
    @Id
    @Column(name = "delivery_id", nullable = false)
    private UUID id;

    @Column(name = "order_id" )
    private UUID orderId;

    @Column(name = "status" )
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Column(name = "departure" )
    private UUID departure;

    @Column(name = "arrival" )
    private UUID arrival;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "recipient" )
    private UUID recipient;

    @Column(name = "slack_acoount", length = 50)
    private String slackAccount;

    @Column(name = "deliver_id" )
    private UUID deliverId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 50)
    private String deletedBy;

    public Delivery(UUID departure, UUID arrival, String address, UUID recipient, UUID orderId, String slackAccount, UUID deliverId) {
        this.departure = departure;
        this.arrival = arrival;
        this.address = address;
        this.recipient = recipient;
        this.orderId = orderId;
        this.slackAccount = slackAccount;
        this.deliverId = deliverId;
        this.status = DeliveryStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
