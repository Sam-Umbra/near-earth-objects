package dev.umbra.space_radar_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object representing the prediction result from the ML model.
 * Contains the hazard assessment, confidence level, and processing status.
 * <p>
 * 
 * @param isPotentiallyHazardous indicates if the asteroid is classified as dangerous
 * @param confidenceScore the probability or reliability of the prediction
 * @param status the operational status of the prediction request
 */
public record PredictionResponse(
        /** Indicates if the asteroid is potentially hazardous. */
        @JsonProperty("is_hazardous") boolean isPotentiallyHazardous,

        /** The confidence score of the prediction (0.0 to 1.0). */
        @JsonProperty("confidence_score") double confidenceScore,

        /** The status of the prediction process. */
        @JsonProperty("status") String status) {
}