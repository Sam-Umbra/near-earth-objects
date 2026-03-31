package dev.umbra.space_radar_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AsteroidData(
        @JsonProperty("est_diameter_min") double estDiameterMin,

        @JsonProperty("est_diameter_max") double estDiameterMax,

        @JsonProperty("relative_velocity") double relativeVelocity,

        @JsonProperty("miss_distance") double missDistance,

        @JsonProperty("absolute_magnitude") double absoluteMagnitude) {
}