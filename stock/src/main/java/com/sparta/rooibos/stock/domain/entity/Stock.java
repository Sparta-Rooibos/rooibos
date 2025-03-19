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





    public static Stock create(String hubId, String productId, int productQuantity) {
        return new Stock(hubId, productId, productQuantity);
    }


    public Stock(String hubId, String productId, int productQuantity) {
        this.hubId = hubId;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.createBy = "계정 아이디";
    }

    public void update(int quantity) {
        this.productQuantity = quantity;
        this.updateBy = "수정된 계정 아이디";
    }


}
