package sparta.rooibos.route.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_route")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Route {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID routeId;

    private UUID fromHubId;

    private UUID toHubId;

    private String distance;

    // hh:mm:ss
    private String timeCost;

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

    public static Route of(UUID fromHubId, UUID toHubId, String distance, String timeCost) {
        return Route.builder()
                .fromHubId(fromHubId)
                .toHubId(toHubId)
                .distance(distance)
                .timeCost(timeCost)
                .build();
    }

    public Route update(Route route) {
        this.fromHubId = route.getFromHubId();
        this.toHubId = route.getToHubId();
        this.distance = route.getDistance();
        this.timeCost = route.getTimeCost();

        return this;
    }

    public void delete() {
        this.deleteAt = LocalDateTime.now();
    }
}
