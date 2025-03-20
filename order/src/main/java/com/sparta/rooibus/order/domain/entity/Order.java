package com.sparta.rooibus.order.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "p_order")  // DB 테이블 이름 p_order와 매핑
@Getter
@NoArgsConstructor
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


    @Column(name = "created_at", nullable = false)
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

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();  // 현재 시간으로 설정
        }
    }

    public static Order create(UUID receiveClientId, UUID requestClientId,UUID productId, int quantity,
        String requirement) {
        Order order = new Order();
        order.receiveClientId = receiveClientId;
        order.requestClientId = requestClientId;
        order.productId = productId;
        order.quantity = quantity;
        order.requirement = requirement;

//        TODO : 생성일과 생성자 추가
        return order;
    }

    public void update(
        UUID requestClientId,
        UUID receiveClientId,
        UUID productId,
        Integer quantity,
        String requirement) {
        if(requestClientId!=null)
            this.requestClientId = requestClientId;
        if(receiveClientId!=null)
            this.receiveClientId = receiveClientId;
        if(productId!=null)
            this.productId = productId;
        if(quantity!=null)
            this.quantity = quantity;
        if(requirement!=null)
            this.requirement = requirement;
        this.updatedAt = LocalDateTime.now();
    }


    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void setDeliveryInfo(UUID deliveryId, UUID departureId) {
        this.deliveryId = deliveryId;
        this.manageHubID = departureId;
    }
}
