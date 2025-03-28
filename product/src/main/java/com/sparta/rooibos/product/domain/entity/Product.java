package com.sparta.rooibos.product.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "p_product")
public class Product {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    private String clientId;

    private String managedHubId;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createAt;

    @CreatedBy
    @Column(updatable = false, nullable = false)
    private String createBy;

    @UpdateTimestamp
    @Column
    private LocalDateTime updateAt;

    @LastModifiedBy
    @Column
    private String updateBy;

    @Column
    private LocalDateTime deleteAt;

    @Column
    private String deleteBy;

    public Product(String name, String clientId, String managedHubId, String userId) {
        this.name = name;
        this.clientId = clientId;
        this.managedHubId = managedHubId;
        this.createBy = userId;
    }

    public void update(String name, String email) {
        this.name = name;
        this.updateBy = email;
    }

    public void delete(String userId) {
        this.deleteBy = userId;
        this.deleteAt = LocalDateTime.now();
    }
}
