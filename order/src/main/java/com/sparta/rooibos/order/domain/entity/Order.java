package com.sparta.rooibos.order.domain.entity;

import com.sparta.rooibos.order.domain.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_order")  // DB 테이블 이름 p_order와 매핑
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id", nullable = false, columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(name = "request_client_id")
    private UUID requestClientId;

    @Column(name = "receive_client_id")
    private UUID receiveClientId;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "requirement", length = 255)
    private String requirement;

    @Column(name = "managed_hub_id")
    private UUID manageHubID;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
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


    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public static Order create(UUID receiveClientId, UUID requestClientId,UUID productId, int quantity,
        String requirement) {
        Order order = new Order();
        order.receiveClientId = receiveClientId;
        order.requestClientId = requestClientId;
        order.productId = productId;
        order.quantity = quantity;
        order.requirement = requirement;
        return order;
    }

    public void update(
        OrderStatus status) {
        this.status = status;
    }


    public void delete(String email) {
        this.deletedBy = email;
        this.deletedAt = LocalDateTime.now();
    }

    public void setDeliveryInfo(UUID deliveryId, UUID departureId) {
        this.deliveryId = deliveryId;
        this.manageHubID = departureId;
    }
}
