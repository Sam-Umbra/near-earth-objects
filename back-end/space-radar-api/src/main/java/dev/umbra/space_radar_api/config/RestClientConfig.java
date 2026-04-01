package dev.umbra.space_radar_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Configuration class for the RestClient.
 * It defines the bean used for making synchronous HTTP requests with specific timeout settings.
 */
@Configuration
public class RestClientConfig {

    /**
     * Creates and configures a RestClient bean.
     * Sets connection and read timeouts to 3 seconds.
     * 
     * @return a configured RestClient instance
     */
    @Bean
    public RestClient restClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);

        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }
}