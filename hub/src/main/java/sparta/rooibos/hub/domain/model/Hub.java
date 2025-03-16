package sparta.rooibos.hub.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

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
}
