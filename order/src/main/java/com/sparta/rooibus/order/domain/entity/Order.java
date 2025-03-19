package com.sparta.rooibus.order.domain.entity;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequest;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequest;
import com.sparta.rooibus.order.domain.model.OrderStatus;
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
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "p_order")  // DB 테이블 이름 p_order와 매핑
@Where(clause = "deleted_at IS NULL")
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

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

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

        order.status = OrderStatus.PENDING; // 기본값 설정
//        TODO : 생성일과 생성자 추가
        return order;
    }

    public void setDeliveryID(UUID deliveryID){
        this.deliveryId = deliveryID;
    }

    public Order(CreateOrderRequest requestDTO){
        this.productId = requestDTO.productId();
        this.quantity = requestDTO.quantity();
        this.requirement = requestDTO.requirement();
        this.status = OrderStatus.PENDING;

        this.updatedAt = LocalDateTime.now();
    }

    public void update(UpdateOrderRequest requestDTO) {
        if(requestDTO.requestClientId()!=null)
            this.requestClientId = requestDTO.requestClientId();
        if(requestDTO.productId()!=null)
            this.productId = requestDTO.productId();
        if(requestDTO.quantity()!=null)
            this.quantity = requestDTO.quantity();
        if(requestDTO.requirement()!=null)
            this.requirement = requestDTO.requirement();
    }

    public void updateStatus(String status){
        switch (status){
            case "주문 접수":
                this.status = OrderStatus.PENDING;
                break;
            case "주문 처리중":
                this.status = OrderStatus.PROCESSING;
                break;
            case "배송 중":
                this.status = OrderStatus.DELIVERING;
                break;
            case "배송 완료":
                this.status = OrderStatus.DELIVERED;
                break;
        }
    this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.status = OrderStatus.CANCELLED;
        this.deletedAt = LocalDateTime.now();
    }
}
