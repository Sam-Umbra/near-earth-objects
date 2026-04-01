package dev.umbra.space_radar_api.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import dev.umbra.space_radar_api.models.dtos.PredictionResponse;

/**
 * Service responsible for handling real-time communication via WebSockets.
 * It broadcasts asteroid analysis results to connected clients.
 */
@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Constructs a new WebSocketService with the required messaging template.
     * @param messagingTemplate the template used to send messages to WebSocket destinations
     */
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Broadcasts the result of an asteroid hazard prediction to the radar topic.
     * @param result the prediction response containing hazard analysis and metadata
     */
    public void radarAnalysis(PredictionResponse result) {
        messagingTemplate.convertAndSend("/radar/prediction", result);
        System.out.println("Successful transmission. Is the asteroid potentially dangerous: " + result.isPotentiallyHazardous());
    }
}
