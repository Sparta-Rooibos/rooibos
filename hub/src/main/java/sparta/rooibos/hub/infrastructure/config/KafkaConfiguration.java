package sparta.rooibos.hub.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic hubDeletedTopic() {
        return new NewTopic("hub.deleted", 1, (short) 1);
    }
}
