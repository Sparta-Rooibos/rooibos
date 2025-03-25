package sparta.rooibos.route.presentation.adapter.in.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sparta.rooibos.route.application.port.in.RouteService;

@Component
@RequiredArgsConstructor
public class HubDeletedActivityListener {

    private final ObjectMapper objectMapper;
    private final RouteService routeService;

    @KafkaListener(topics = "hub.deleted", groupId = "route-server")
    public void listen(String message) {
        try {
            HubDeletedActivity hubDeletedActivity = objectMapper.readValue(message, HubDeletedActivity.class);
            if (hubDeletedActivity.deletedAt() != null) {
                routeService.deleteRouteByHubDeletedActivity(hubDeletedActivity.hubId());
            }
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e);
        }
    }
}
