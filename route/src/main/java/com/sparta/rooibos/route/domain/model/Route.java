package com.sparta.rooibos.route.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_route")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Route {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID routeId;

    private UUID fromHubId;

    private UUID toHubId;

    private String fromHubName;

    private String toHubName;

    private Integer distance;

    private Integer timeCost;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false, nullable = false)
    private String createdBy;

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column
    private String updatedBy;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private String deletedBy;

    public static Route of(UUID fromHubId, UUID toHubId, String fromHubName, String toHubName) {
        return Route.builder()
                .fromHubId(fromHubId)
                .toHubId(toHubId)
                .fromHubName(fromHubName)
                .toHubName(toHubName)
                .build();
    }

    public void setDistanceAndDuration(int distance, int timeCost) {
        this.distance = distance;
        this.timeCost = timeCost;
    }

    public Route update(Route route) {
        this.fromHubId = route.getFromHubId();
        this.toHubId = route.getToHubId();
        this.fromHubName = route.getFromHubName();
        this.toHubName = route.getToHubName();
        this.distance = route.getDistance();
        this.timeCost = route.getTimeCost();

        return this;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
