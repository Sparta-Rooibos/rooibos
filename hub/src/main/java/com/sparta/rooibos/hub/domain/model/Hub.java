package com.sparta.rooibos.hub.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "p_hub")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
// TODO base entity 상속
public class Hub {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID hubId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String address;

    // TODO Point 사용할지 고민 -> 지도 라이브러리에 따라 다를듯?
    private String latitude;

    // TODO Point 사용할지 고민 -> 지도 라이브러리에 따라 다를듯?
    private String longitude;

    @OneToMany(mappedBy = "hub", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HubManager> hubManagers;

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

    public static Hub of(String name, String region, String address) {
        return Hub.builder()
                .name(name)
                .region(region)
                .address(address)
                .build();
    }

    public Hub setCoordinates(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        return this;
    }

    public Hub update(Hub hub) {
        this.name = hub.getName();
        this.region = hub.getRegion();
        this.address = hub.getAddress();
        this.latitude = hub.getLatitude();
        this.longitude = hub.getLongitude();

        return this;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
