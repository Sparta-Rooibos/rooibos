package com.sparta.rooibos.route.infrastructure.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.sparta.rooibos.route.application.dto.response.direction.GetGeoDirectionResponse;
import com.sparta.rooibos.route.application.dto.response.hub.HubClientResponse;
import com.sparta.rooibos.route.application.port.out.GeoDirectionService;
import com.sparta.rooibos.route.domain.model.Route;
import com.sparta.rooibos.route.domain.repository.RouteRepository;
import com.sparta.rooibos.route.infrastructure.client.HubFeignClient;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RouteDataGenerator {

    private final HubFeignClient hubFeignClient;
    private final GeoDirectionService geoDirectionService;
    private final RouteRepository routeRepository;

    @Bean
    public CommandLineRunner init() {
        return args -> {

            Map<String, List<String>> regions = new HashMap<>();

            regions.put(
                    "경기남부",
                    Arrays.asList(
                            "경기북부", "서울", "인천", "강원도", "경상북도", "대전", "대구"
                    )
            );

            regions.put(
                    "대전",
                    Arrays.asList(
                            "충청남도", "충청북도", "세종", "전라북도", "광주", "전라남도", "경기남부", "대구"
                    )
            );

            regions.put(
                    "대구",
                    Arrays.asList(
                            "경상북도", "경상남도", "부산", "울산", "경상북도", "경기남부", "대전"
                    )
            );

            regions.put(
                    "경상북도",
                    Arrays.asList(
                            "경기남부", "대구"
                    )
            );

            for (Map.Entry<String, List<String>> region : regions.entrySet()) {
                String fromHubRegion = region.getKey();
                List<String> toHubRegions = region.getValue();

                HubClientResponse fromHub = hubFeignClient.getHubByRegion(fromHubRegion);
                UUID fromHubId = fromHub.hubId();
                String fromHubName = fromHub.name();
                String start = fromHub.getCoordinates();

                for (String toHubRegion : toHubRegions) {
                    HubClientResponse toHub = hubFeignClient.getHubByRegion(toHubRegion);
                    UUID toHubId = toHub.hubId();
                    String toHubName = toHub.name();
                    String goal = toHub.getCoordinates();

                    GetGeoDirectionResponse result =
                            geoDirectionService.getGeoDirection(start, goal);

                    Route forwardRoute = Route.of(fromHubId, toHubId, fromHubName, toHubName);
                    forwardRoute.setDistanceAndDuration(result.getDistance(), result.getDuration());
                    routeRepository.createRoute(forwardRoute);

                    Route reverseRoute = Route.of(toHubId, fromHubId, toHubName, fromHubName);
                    reverseRoute.setDistanceAndDuration(result.getDistance(), result.getDuration());
                    routeRepository.createRoute(reverseRoute);
                }
            }

        };
    }
}
