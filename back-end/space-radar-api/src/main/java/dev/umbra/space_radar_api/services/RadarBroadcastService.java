package dev.umbra.space_radar_api.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import dev.umbra.space_radar_api.models.dtos.PredictionResponse;

@Service
public class RadarBroadcastService {

    private final SimpMessagingTemplate messagingTemplate;

    public RadarBroadcastService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastAnalysis(PredictionResponse result) {
        messagingTemplate.convertAndSend("/radar/prediction", result);
        System.out.println("Succesful transmission. Is the asteroid dangerous: " + result.isHazardous());
    }

}
