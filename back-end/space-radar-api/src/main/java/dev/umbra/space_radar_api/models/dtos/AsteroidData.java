package dev.umbra.space_radar_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import tools.jackson.databind.JsonNode;

/**
 * Data Transfer Object representing relevant physical and orbital data of an asteroid.
 * This record is used to transport data from the NASA API to the ML prediction model.
 * 
 * @param estDiameterMin minimum estimated diameter in kilometers
 * @param estDiameterMax maximum estimated diameter in kilometers
 * @param relativeVelocity velocity relative to Earth in km/h
 * @param missDistance distance by which the object misses Earth in kilometers
 * @param absoluteMagnitude the intrinsic brightness of the celestial object
 */
public record AsteroidData(
                /** Minimum estimated diameter. */
                @JsonProperty("est_diameter_min") double estDiameterMin,

                /** Maximum estimated diameter. */
                @JsonProperty("est_diameter_max") double estDiameterMax,

                /** Relative velocity in km/h. */
                @JsonProperty("relative_velocity") double relativeVelocity,

                /** Miss distance in kilometers. */
                @JsonProperty("miss_distance") double missDistance,

                /** Absolute magnitude (H). */
                @JsonProperty("absolute_magnitude") double absoluteMagnitude) {

        /** Standard unit for distance measurements. */
        private static final String stdMeasurementUnit = "kilometers";
        
        /** Standard unit for velocity measurements. */
        private static final String stdVelocity = "kilometers_per_hour";

        /**
         * Factory method that maps a raw JSON node from NASA's API to an AsteroidData instance.
         * 
         * @param asteroid the JsonNode containing raw asteroid data from NASA
         * @return a new AsteroidData instance populated with the extracted values
         */
        public static AsteroidData fromEntity(JsonNode asteroid) {
                return new AsteroidData(
                                asteroid.path("estimated_diameter").path(stdMeasurementUnit)
                                                .path("estimated_diameter_min")
                                                .asDouble(),
                                asteroid.path("estimated_diameter").path(stdMeasurementUnit)
                                                .path("estimated_diameter_max")
                                                .asDouble(),
                                asteroid.path("close_approach_data").get(0).path("relative_velocity").path(stdVelocity)
                                                .asDouble(),
                                asteroid.path("close_approach_data").get(0).path("miss_distance")
                                                .path(stdMeasurementUnit)
                                                .asDouble(),
                                asteroid.path("absolute_magnitude_h").asDouble());
        }

}