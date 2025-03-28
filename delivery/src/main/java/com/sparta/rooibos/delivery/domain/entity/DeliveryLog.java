package com.sparta.rooibos.delivery.domain.entity;

import com.sparta.rooibos.delivery.domain.model.DeliveryLogEnum;
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
@Table(name = "p_deliverylog")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DeliveryLog {
    @Id
    @GeneratedValue
    @Column(name = "deliverylog_id", nullable = false, columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(name = "delivery_id", nullable = false)
    private UUID deliveryId;

    @Column(name = "departure")
    private String departure;

    @Column(name = "fromHubId")
    private UUID fromHubId;

    @Column(name = "arrival")
    private String arrival;

    @Column(name = "toHubId")
    private UUID toHubId;

    @Column(name = "sequence", length = 255)
    private int sequence;

    @Column(name = "expected_distance", length = 50)
    private String expectedDistance;

    @Column(name = "expected_time", length = 50)
    private String expectedTime;

    @Column(name = "distance", length = 50)
    private String takenDistance;

    @Column(name = "taken_time", length = 50)
    private String takenTime;

    @Column(name = "status", length = 50)
    private DeliveryLogEnum status;

    @Column(name = "deliver")
    private UUID deliver;

    @CreatedBy
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_by", length = 50)
    private String deletedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static DeliveryLog of( UUID fromHubId, UUID toHubId, String departure, String arrival, int sequence, String expectedDistance, String expectedTime) {
        DeliveryLog deliveryLog = new DeliveryLog();
        deliveryLog.fromHubId = fromHubId;
        deliveryLog.toHubId = toHubId;
        deliveryLog.departure = departure;
        deliveryLog.arrival = arrival;
        deliveryLog.sequence = sequence;
        deliveryLog.expectedDistance = expectedDistance;
        deliveryLog.expectedTime = expectedTime;
        deliveryLog.status = DeliveryLogEnum.PENDING;
        return deliveryLog;
    }
// 마스터 권한으로 배송자 배정
    public void setDeliver(UUID deliverId){
        this.deliver = deliverId;
    }

    public void setStatus(String status) {
        DeliveryLogEnum deliveryStatus = DeliveryLogEnum.valueOf(status);
        this.status = deliveryStatus;
    }

    public void delete(String email) {
        this.deletedAt =LocalDateTime.now();
        this.deletedBy = email;
    }
}
