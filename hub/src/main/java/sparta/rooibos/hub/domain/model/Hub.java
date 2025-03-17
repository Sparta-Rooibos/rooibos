package sparta.rooibos.hub.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_hub")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
// TODO base entity 상속
public class Hub {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID hubId;

    private String name;

    private String region;

    private String address;

    // TODO Point 사용할지 고민 -> 지도 라이브러리에 따라 다를듯?
    private String latitude;

    // TODO Point 사용할지 고민 -> 지도 라이브러리에 따라 다를듯?
    private String longitude;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createAt;

    @CreatedBy
    @Column(updatable = false, nullable = false)
    private String createBy;

    @LastModifiedDate
    @Column
    private LocalDateTime updateAt;

    @LastModifiedBy
    @Column
    private String updateBy;

    @Column
    private LocalDateTime deleteAt;

    @Column
    private String deleteBy;

    public Hub update(Hub hub) {
        if (!isSameHub(hubId)) {
            // TODO 커스텀 예외로 전환
            throw new IllegalArgumentException("Hub IDs do not match");
        }

        this.name = hub.getName();
        this.region = hub.getRegion();
        this.address = hub.getAddress();
        this.latitude = hub.getLatitude();
        this.longitude = hub.getLongitude();

        return this;
    }

    private boolean isSameHub(UUID hubId) {
        return this.hubId.equals(hubId);
    }

    public void delete() {
        this.deleteAt = LocalDateTime.now();
    }
}
