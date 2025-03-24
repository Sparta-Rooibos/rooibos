package sparta.rooibos.hub.infrastructure.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientRetryConfig {

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100L, 1000L, 5);
    }
}
