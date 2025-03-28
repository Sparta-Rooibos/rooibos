package com.sparta.rooibos.hub.infrastructure.init;

import com.sparta.rooibos.hub.application.dto.hub.request.CreateHubRequest;
import com.sparta.rooibos.hub.application.port.in.HubService;
import com.sparta.rooibos.hub.domain.respository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubDataGenerator {

    private final HubService hubService;

    private final HubRepository hubRepository;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            hubService.createHub(new CreateHubRequest(
                    "서울특별시 센터",
                    "서울",
                    "서울특별시 송파구 송파대로 55"
            ));

            hubService.createHub(new CreateHubRequest(
                    "경기 북부 센터",
                    "경기북부",
                    "경기도 고양시 덕양구 권율대로 570"
            ));

            hubService.createHub(new CreateHubRequest(
                    "경기 남부 센터",
                    "경기남부",
                    "경기도 이천시 덕평로 257-21"
            ));

            hubService.createHub(new CreateHubRequest(
                    "부산광역시 센터",
                    "부산",
                    "부산 동구 중앙대로 206"
            ));

            hubService.createHub(new CreateHubRequest(
                    "대구광역시 센터",
                    "대구",
                    "대구 북구 태평로 161"
            ));

            hubService.createHub(new CreateHubRequest(
                    "인천광역시 센터",
                    "인천",
                    "인천 남동구 정각로 29"
            ));

            hubService.createHub(new CreateHubRequest(
                    "광주광역시 센터",
                    "광주",
                    "광주 서구 내방로 111"
            ));

            hubService.createHub(new CreateHubRequest(
                    "대전광역시 센터",
                    "대전",
                    "대전 서구 둔산로 100"
            ));

            hubService.createHub(new CreateHubRequest(
                    "울산광역시 센터",
                    "울산",
                    "울산 남구 중앙로 201"
            ));

            hubService.createHub(new CreateHubRequest(
                    "세종특별자치시 센터",
                    "세종",
                    "세종특별자치시 한누리대로 2130"
            ));

            hubService.createHub(new CreateHubRequest(
                    "강원특별자치도 센터",
                    "강원도",
                    "강원특별자치도 춘천시 중앙로 1"
            ));

            hubService.createHub(new CreateHubRequest(
                    "충청북도 센터",
                    "충청북도",
                    "충북 청주시 상당구 상당로 82"
            ));

            hubService.createHub(new CreateHubRequest(
                    "충청남도 센터",
                    "충청남도",
                    "충남 홍성군 홍북읍 충남대로 21"
            ));

            hubService.createHub(new CreateHubRequest(
                    "전북특별자치도 센터",
                    "전라북도",
                    "전북특별자치도 전주시 완산구 효자로 225"
            ));

            hubService.createHub(new CreateHubRequest(
                    "전라남도 센터",
                    "전라남도",
                    "전남 무안군 삼향읍 오룡길 1"
            ));

            hubService.createHub(new CreateHubRequest(
                    "경상북도 센터",
                    "경상북도",
                    "경북 안동시 풍천면 도청대로 455"
            ));

            hubService.createHub(new CreateHubRequest(
                    "경상남도 센터",
                    "경상남도",
                    "경남 창원시 의창구 중앙대로 300"
            ));
        };
    }
}
