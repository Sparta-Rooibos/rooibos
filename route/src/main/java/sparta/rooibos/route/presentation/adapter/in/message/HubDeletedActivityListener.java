package sparta.rooibos.route.presentation.adapter.in.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubDeletedActivityListener {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "hub.deleted", groupId = "route-server")
    public void listen(String message) {
        try {
            HubDeletedActivity hubDeletedActivity = objectMapper.readValue(message, HubDeletedActivity.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
