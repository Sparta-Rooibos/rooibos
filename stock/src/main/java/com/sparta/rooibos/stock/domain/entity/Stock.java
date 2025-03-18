package com.sparta.rooibos.stock.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "p_stock")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Stock {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;
    private String hubId;
    private String productId;
    private String productQuantity;

    private String createdBy;
    private LocalDateTime createdAt;

    private String updatedBy;
    private LocalDateTime updatedAt;

    private String deletedBy;
    private LocalDateTime deletedAt;

}
