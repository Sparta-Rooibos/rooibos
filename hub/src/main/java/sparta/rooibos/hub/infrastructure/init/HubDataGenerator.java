package sparta.rooibos.hub.infrastructure.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.respository.HubRepository;

@Component
@RequiredArgsConstructor
public class HubDataGenerator {

    private final HubRepository hubRepository;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            hubRepository.createHub(Hub.builder()
                    .name("서울특별시 센터")
                    .region("서울")
                    .address("서울특별시 송파구 송파대로 55")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("경기 북부 센터")
                    .region("경기북부")
                    .address("경기도 고양시 덕양구 권율대로 570")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("경기 남부 센터")
                    .region("경기남부")
                    .address("경기도 이천시 덕평로 257-21")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("부산광역시 센터")
                    .region("부산")
                    .address("부산 동구 중앙대로 206")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("대구광역시 센터")
                    .region("대구")
                    .address("대구 북구 태평로 161")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("인천광역시 센터")
                    .region("인천")
                    .address("인천 남동구 정각로 29")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("광주광역시 센터")
                    .region("광주")
                    .address("광주 서구 내방로 111")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("대전광역시 센터")
                    .region("대전")
                    .address("대전 서구 둔산로 100")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("울산광역시 센터")
                    .region("울산")
                    .address("울산 남구 중앙로 201")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("세종특별자치시 센터")
                    .region("세종")
                    .address("세종특별자치시 한누리대로 2130")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("강원특별자치도 센터")
                    .region("강원도")
                    .address("강원특별자치도 춘천시 중앙로 1")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("충청북도 센터")
                    .region("충청북도")
                    .address("충북 청주시 상당구 상당로 82")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("충청남도 센터")
                    .region("충청남도")
                    .address("충남 홍성군 홍북읍 충남대로 21")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("전북특별자치도 센터")
                    .region("전라북도")
                    .address("전북특별자치도 전주시 완산구 효자로 225")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("전라남도 센터")
                    .region("전라남도")
                    .address("전남 무안군 삼향읍 오룡길 1")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("경상북도 센터")
                    .region("경상북도")
                    .address("경북 안동시 풍천면 도청대로 455")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );

            hubRepository.createHub(Hub.builder()
                    .name("경상남도 센터")
                    .region("경상남도")
                    .address("경남 창원시 의창구 중앙대로 300")
                    .latitude(null)
                    .longitude(null)
                    .build()
            );
        };
    }
}
