package dev.umbra.space_radar_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dev.umbra.space_radar_api.models.dtos.AsteroidData;
import dev.umbra.space_radar_api.models.dtos.PredictionResponse;

@Service
public class ModelIntegrationService {

    private final RestTemplate restTemplate;
    private final String modelUrl;

    public ModelIntegrationService(@Value("${ml_http_url}") String modelUrl) {
        this.restTemplate = new RestTemplate();
        this.modelUrl = modelUrl;
    }

    public PredictionResponse predictAsteroidHazard(AsteroidData data) {
        try {
            return restTemplate.postForObject(modelUrl, data, PredictionResponse.class);
        } catch (RestClientException e) {
            System.err.println("Communication error with prediction model: " + e.getMessage());
            throw new RestClientException("Prediction model inaccessible", e);
        }
    }

}
