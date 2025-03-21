package sparta.rooibos.hub.infrastructure.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverFeignClientConfig {

    @Value("${naver.coordinate.id}")
    private String apiClientId;

    @Value("${naver.coordinate.secret}")
    private String apiClientSecret;

    @Bean
    public RequestInterceptor naverRequestInterceptor() {
        return template -> {
            template.header("x-ncp-apigw-api-key-id", apiClientId);
            template.header("x-ncp-apigw-api-key", apiClientSecret);
            template.header("Accept", "application/json");
        };
    }
}
