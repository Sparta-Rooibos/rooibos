package com.sparta.rooibus.order.domain.entity;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequestDTO;
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
    @Column(name = "order_id", nullable = false)
    private UUID id;  // 주문 ID

    @Column(name = "request_client_id")
    private UUID requestClientId;  // 공급업체 ID

    @Column(name = "response_client_id")
    private UUID responseClientId;  // 수령업체 ID

    @Column(name = "product_id")
    private UUID productId;  // 상품 ID

    @Column(name = "quantity")
    private int quantity;  // 수량

    @Column(name = "delivery_id")
    private UUID deliveryId;  // 배송 ID

    @Column(name = "requirement", length = 255)
    private String requirement;  // 요청사항

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // 주문상태

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // 생성일

    @Column(name = "created_by", length = 50)
    private String createdBy;  // 생성자

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 수정일

    @Column(name = "updated_by", length = 50)
    private String updatedBy;  // 수정자

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;  // 삭제일

    @Column(name = "deleted_by", length = 50)
    private String deletedBy;  // 삭제자

    // 주문 저장시 UUID 생성해서 저장
    // TODO : 저장할때 생성자 찾아서 생성자도 넣기
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();  // UUID가 없는 경우 자동 생성
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 주문 수정 시 수정 일시 업데이트
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Order(CreateOrderRequestDTO requestDTO){
        this.requestClientId = requestDTO.requestClientId();
        this.responseClientId = requestDTO.responseClientId();
        this.productId = requestDTO.productId();
        this.quantity = requestDTO.quantity();
        this.requirement = requestDTO.requirement();
        this.status = OrderStatus.PENDING;

    // TODO : 저장할때 수정자 찾아서 수정자 넣기
        this.updatedAt = LocalDateTime.now();
    }

    public void update(UpdateOrderRequestDTO requestDTO) {
        if(requestDTO.requestClientId()!=null)
            this.requestClientId = requestDTO.requestClientId();
        if(requestDTO.responseClientId()!=null)
            this.responseClientId = requestDTO.responseClientId();
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
            case "주문 처리중":
                this.status = OrderStatus.PROCESSING;
            case "배송 중":
                this.status = OrderStatus.DELIVERING;
            case "배송 완료":
                this.status = OrderStatus.DELIVERED;
            case "취소":
                this.status = OrderStatus.CANCELLED;
        }


    }
}
