package dev.umbra.space_radar_api.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.umbra.space_radar_api.models.dtos.AsteroidData;
import dev.umbra.space_radar_api.models.dtos.PredictionResponse;
import dev.umbra.space_radar_api.services.ModelIntegrationService;
import dev.umbra.space_radar_api.services.NasaService;
import dev.umbra.space_radar_api.services.WebSocketService;

/**
 * Orchestrator component that manages the periodic scanning of space for asteroids.
 * It coordinates data retrieval from NASA, hazard prediction via ML, and real-time broadcasting.
 */
@Component
public class RadarOrchestrator {

    @Autowired
    private ModelIntegrationService modelService;

    @Autowired
    private WebSocketService broadcastService;

    @Autowired
    private NasaService nasaService;

    /**
     * Scheduled task that performs a space scan every 10 seconds.
     * Fetches the next asteroid, predicts its hazard level, and broadcasts the result.
     */
    @Scheduled(fixedRate = 10000)
    public void scanSpace() {
        AsteroidData asteroid = nasaService.getNextAsteroid();

        if (asteroid == null) {
            System.out.println("The space is empty. NASA didn't provide any data.");
            return;
        };

        PredictionResponse prediction = modelService.predictAsteroidHazard(asteroid);
        
        broadcastService.radarAnalysis(prediction);
    }

}
