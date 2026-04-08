package dev.umbra.space_radar_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.JsonNode;

/**
 * Data Transfer Object representing relevant physical and orbital data of an
 * asteroid.
 * This record is used to transport data from the NASA API to the ML prediction
 * model.
 * 
 * @param id                asteroid id from NASA API
 * 
 * @param name              the designation or common name of the asteroid
 * @param estDiameterMin    minimum estimated diameter in kilometers
 * @param estDiameterMax    maximum estimated diameter in kilometers
 * @param relativeVelocity  velocity relative to Earth in km/h
 * @param missDistance      distance by which the object misses Earth in
 *                          kilometers
 * @param absoluteMagnitude the intrinsic brightness of the celestial object
 */
public record AsteroidData(
                /** Unique identifier assigned by the NASA JPL system. */
                int id,

                /** The primary designation or name of the celestial body. */
                String name,

                /** Minimum estimated diameter in kilometers. */
                @JsonProperty("est_diameter_min") double estDiameterMin,

                /** Maximum estimated diameter in kilometers. */
                @JsonProperty("est_diameter_max") double estDiameterMax,

                /** Velocity relative to Earth in km/h at the point of closest approach. */
                @JsonProperty("relative_velocity") double relativeVelocity,

                /** The distance between the asteroid and Earth at its closest point in km. */
                @JsonProperty("miss_distance") double missDistance,

                /** Absolute magnitude (H), measuring the asteroid's intrinsic luminosity. */
                @JsonProperty("absolute_magnitude") double absoluteMagnitude) {

        /**
         * * The measurement key used to extract kilometer-based values
         * from the NASA API JSON structure.
         */
        private static final String stdMeasurementUnit = "kilometers";

        /**
         * * The measurement key used to extract velocity in kilometers per hour
         * from the NASA API JSON structure.
         */
        private static final String stdVelocity = "kilometers_per_hour";

        /**
         * Factory method that maps a raw JSON node from NASA's API to an AsteroidData
         * instance.
         * * @param asteroid the JsonNode containing raw asteroid data from NASA
         * 
         * @return a new AsteroidData instance populated with the extracted values
         */
        public static AsteroidData fromEntity(JsonNode asteroid) {
                return new AsteroidData(
                                asteroid.path("id").asInt(),
                                asteroid.path("name").asString(),
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