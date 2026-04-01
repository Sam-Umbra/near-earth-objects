package dev.umbra.space_radar_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object representing the prediction result from the ML model.
 * Contains the hazard assessment, confidence level, and processing status.
 * <p>
 * Objeto de Transferência de Dados que representa o resultado da previsão do modelo de ML.
 * Contém a avaliação de perigo, o nível de confiança e o status do processamento.
 * 
 * @param isPotentiallyHazardous indicates if the asteroid is classified as dangerous / indica se o asteroide é classificado como perigoso
 * @param confidenceScore the probability or reliability of the prediction / a probabilidade ou confiabilidade da previsão
 * @param status the operational status of the prediction request / o status operacional da requisição de previsão
 */
public record PredictionResponse(
        /** Indicates if the asteroid is potentially hazardous. / Indica se o asteroide é potencialmente perigoso. */
        @JsonProperty("is_hazardous") boolean isPotentiallyHazardous,

        /** The confidence score of the prediction (0.0 to 1.0). / O score de confiança da previsão (0.0 a 1.0). */
        @JsonProperty("confidence_score") double confidenceScore,

        /** The status of the prediction process. / O status do processo de previsão. */
        @JsonProperty("status") String status) {
}