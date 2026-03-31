package dev.umbra.space_radar_api.schedulers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.umbra.space_radar_api.models.dtos.AsteroidData;
import dev.umbra.space_radar_api.models.dtos.PredictionResponse;
import dev.umbra.space_radar_api.services.ModelIntegrationService;
import dev.umbra.space_radar_api.services.RadarBroadcastService;

@Component
@EnableScheduling
public class RadarOrchestrator {

    @Autowired
    private ModelIntegrationService modelService;

    @Autowired
    private RadarBroadcastService broadcastService;

    private final Random random = new Random();

    @Scheduled(fixedRate = 5000)
    public void scanSpace() {
        AsteroidData newAsteroid = new AsteroidData(
                random.nextDouble(0.01, 3.0), // min_diameter
                random.nextDouble(0.05, 3.5), // max_diameter
                random.nextDouble(10000, 90000), // velocity
                random.nextDouble(50000, 50000000), // miss_distance
                random.nextDouble(15.0, 30.0) // magnitude
        );

        PredictionResponse analysis = modelService.predictAsteroidHazard(newAsteroid);
        
        broadcastService.broadcastAnalysis(analysis);
    }

}
