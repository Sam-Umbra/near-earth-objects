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
 * <p>
 * Serviço responsável pela integração com o modelo de Machine Learning.
 * Ele envia dados de asteroides para uma API externa para prever perigos potenciais.
 */
@Service
public class ModelIntegrationService {

    private final RestClient restClient;

    /** The URL of the external ML model API. / A URL da API externa do modelo de ML. */
    @Value("${ml_http_url}")
    private String modelUrl;

    /**
     * Constructs a new ModelIntegrationService with the required RestClient.
     * <p>
     * Constrói um novo ModelIntegrationService com o RestClient necessário.
     * 
     * @param restClient the client used to make HTTP requests / o cliente usado para fazer requisições HTTP
     */
    public ModelIntegrationService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Sends asteroid data to the ML model and retrieves the hazard prediction.
     * <p>
     * Envia dados de asteroides para o modelo de ML e recupera a previsão de perigo.
     * 
     * @param data the asteroid data to be analyzed / os dados do asteroide a serem analisados
     * @return the prediction response from the model / a resposta de previsão do modelo
     * @throws RestClientException if the communication with the model fails / se a comunicação com o modelo falhar
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
