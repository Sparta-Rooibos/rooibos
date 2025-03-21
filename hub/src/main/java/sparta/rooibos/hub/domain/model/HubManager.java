package sparta.rooibos.hub.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_hub_manager")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class HubManager {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID hubManagerId;

    private UUID userId;

    private String username;

    private UUID hubId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hub_id")
    private Hub hub;

    private LocalDateTime deletedAt;

    public static HubManager of(UUID userId, Hub hub) {
        return HubManager.builder()
                .userId(userId)
                .hub(hub)
                .build();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
