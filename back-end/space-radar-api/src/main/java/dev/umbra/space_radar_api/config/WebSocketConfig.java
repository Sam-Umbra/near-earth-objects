package dev.umbra.space_radar_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class for WebSocket messaging using STOMP.
 * It enables the message broker and defines the endpoints for client connections.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configures the message broker options.
     * Sets up a simple memory-based message broker to carry messages back to the client 
     * on destinations prefixed with "/radar".
     * 
     * @param config the message broker registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/radar");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registers STOMP endpoints mapping each to a specific URL.
     * 
     * @param registry the registry for STOMP endpoints
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-radar").setAllowedOrigins("*").withSockJS();
    }

}
