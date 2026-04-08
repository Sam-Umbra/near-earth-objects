package dev.umbra.space_radar_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
     * Returns an empty Optional if the model is unreachable or returns an
     * unexpected response,
     * allowing the scheduler to skip gracefully instead of crashing.
     *
     * @param data the asteroid data to be analyzed
     * @return an Optional containing the prediction, or empty if the model is
     *         inaccessible
     */
    public Optional<PredictionResponse> predictAsteroidHazard(AsteroidData data) {
        try {
            PredictionResponse response = restClient.post()
                    .uri(this.modelUrl)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(data)
                    .retrieve()
                    .body(PredictionResponse.class);

            return Optional.ofNullable(response);

        } catch (RestClientException e) {
            System.err.println("[ModelIntegrationService] Prediction model inaccessible: " + e.getMessage());
            return Optional.empty();
        }
    }

}
