package com.sparta.rooibos.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
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
}
