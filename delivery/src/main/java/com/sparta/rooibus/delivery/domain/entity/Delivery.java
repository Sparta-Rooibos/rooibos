package com.sparta.rooibus.delivery.domain.entity;

import com.sparta.rooibus.delivery.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibus.delivery.domain.model.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Optional;
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

    public static Delivery of(UUID orderId, UUID departure, UUID arrival, String address, UUID recipient, String slackAccount, UUID clientDeliverID) {
        Delivery delivery = new Delivery();
        delivery.orderId = orderId;
        delivery.departure = departure;
        delivery.arrival = arrival;
        delivery.address = address;
        delivery.recipient = recipient;
        delivery.slackAccount = slackAccount;
        delivery.clientDeliver = clientDeliverID;
        return delivery;
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

    public void updateStatus(DeliveryStatus status) {
        this.status = status;
    }

    public void delete(){
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = "취소한 사람";// TODO: 로그인한 사람
    }

    public void validateDeletable() {
        if (this.status != DeliveryStatus.PENDING) {
            throw new IllegalStateException("배송 중이거나 완료된 주문은 삭제할 수 없습니다.");
        }
    }

}
