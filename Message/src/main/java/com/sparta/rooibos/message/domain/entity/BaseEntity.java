package com.sparta.rooibos.message.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false, nullable = false)
    protected LocalDateTime createAt;

    @CreatedBy
    @Column(updatable = false, nullable = false)
    protected String createBy;

    @LastModifiedDate
    @Column
    protected LocalDateTime updateAt;

    @LastModifiedBy
    @Column
    protected String updateBy;

    @Column
    protected LocalDateTime deleteAt;

    @Column
    protected String deleteBy;

    public void delete(String deletedBy) {
        this.deleteBy = deletedBy;
        this.deleteAt = LocalDateTime.now();
    }
}
