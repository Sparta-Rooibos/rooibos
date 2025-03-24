package com.sparta.rooibus.delivery.domain.entity;

import com.sparta.rooibus.delivery.domain.model.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "p_delivery")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id", nullable = false, columnDefinition = "UUID DEFAULT gen_random_uuid()")
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

    @Column(name = "recipient_id" )
    private UUID recipient;

    @Column(name = "slack_account", length = 50)
    private String slackAccount;

    @Column(name = "client_deliver_id" )
    private UUID clientDeliver;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 50)
    private String deletedBy;

    public static Delivery of(UUID orderId, UUID departure, UUID arrival, String address, UUID recipient, String slackAccount) {
        Delivery delivery = new Delivery();
        delivery.orderId = orderId;
        delivery.departure = departure;
        delivery.arrival = arrival;
        delivery.address = address;
        delivery.recipient = recipient;
        delivery.slackAccount = slackAccount;
        return delivery;
    }

    public void setClientDeliver(UUID clientDeliver){
        this.clientDeliver = clientDeliver;
    }

    public void updateStatus(DeliveryStatus status) {
        this.status = status;
    }

    public void delete(String email){
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = email;
    }

    public void validateDeletable() {
        if (this.status != DeliveryStatus.PENDING && this.status==DeliveryStatus.CANCELED) {
            throw new IllegalStateException("허브 대기중인 상태에서만 삭제할 수 있습니다.");
        }
    }

}
