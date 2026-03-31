package dev.umbra.space_radar_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PredictionResponse(
        @JsonProperty("is_hazardous") boolean isHazardous,

        @JsonProperty("confidence_score") double confidenceScore,

        @JsonProperty("status") String status) {
}