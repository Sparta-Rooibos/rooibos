package com.sparta.rooibos.stock.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "p_stock")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;
    private String hubId;
    private String productId;
    private int productQuantity;


    public static Stock create(String email, String hubId, String productId, int productQuantity) {
        return new Stock(email, hubId, productId, productQuantity);
    }

    // 테스트 용
    public Stock(UUID id, int productQuantity) {
        this.id = id;
        this.productQuantity = productQuantity;
    }

    public Stock(String email, String hubId, String productId, int productQuantity) {
        this.hubId = hubId;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.createBy = email;
    }

    // db락 비관락 -> 데드락 안걸리게
    // 낙관락 select
    public void update(int quantity, String email) {
        this.productQuantity += quantity;
        this.updateBy = email;
        validateQuantity();
    }


    public void validateQuantity() {
        if (productQuantity < 0) {
            throw new IllegalArgumentException("수량은 0보다 작을 수 없습니다.");
        }
    }

}
