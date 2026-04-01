package dev.umbra.space_radar_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import dev.umbra.space_radar_api.models.dtos.AsteroidData;
import dev.umbra.space_radar_api.models.dtos.PredictionResponse;

/**
 * Service responsible for integrating with the Machine Learning model.
 * It sends asteroid data to an external API to predict potential hazards.
 */
@Service
public class ModelIntegrationService {

    private final RestClient restClient;

    /** The URL of the external ML model API. */
    @Value("${ml_http_url}")
    private String modelUrl;

    /**
     * Constructs a new ModelIntegrationService with the required RestClient.
     * 
     * @param restClient the client used to make HTTP requests
     */
    public ModelIntegrationService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Sends asteroid data to the ML model and retrieves the hazard prediction.
     * 
     * @param data the asteroid data to be analyzed
     * @return the prediction response from the model
     * @throws RestClientException if the communication with the model fails
     */
    public PredictionResponse predictAsteroidHazard(AsteroidData data) {
        try {
            return restClient.post()
                    .uri(this.modelUrl)
                    .body(data)
                    .retrieve()
                    .body(PredictionResponse.class);
        } catch (RestClientException e) {
            System.err.println("Communication error with prediction model: " + e.getMessage());
            throw new RestClientException("Prediction model inaccessible", e);
        }
    }

}
