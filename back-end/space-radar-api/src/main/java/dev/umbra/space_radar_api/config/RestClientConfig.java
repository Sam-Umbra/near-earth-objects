package dev.umbra.space_radar_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Configuration class for the RestClient.
 * It defines the bean used for making synchronous HTTP requests with specific timeout settings.
 * <p>
 * Classe de configuração para o RestClient.
 * Ela define o bean usado para realizar requisições HTTP síncronas com configurações específicas de timeout.
 */
@Configuration
public class RestClientConfig {

    /**
     * Creates and configures a RestClient bean.
     * Sets connection and read timeouts to 3 seconds.
     * <p>
     * Cria e configura um bean RestClient.
     * Define os timeouts de conexão e leitura para 3 segundos.
     * 
     * @return a configured RestClient instance / uma instância configurada do RestClient
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