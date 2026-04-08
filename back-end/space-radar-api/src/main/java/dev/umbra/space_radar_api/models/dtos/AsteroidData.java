package dev.umbra.space_radar_api.models.dtos;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.JsonNode;

/**
 * Data Transfer Object (DTO) representing the physical and orbital
 * characteristics of an asteroid.
 * *
 * <p>
 * This record serves as a bridge between the raw data retrieved from the NASA
 * NeoWs
 * (Near Earth Object Web Service) API and the internal Machine Learning
 * prediction models.
 * It encapsulates dimensions, velocity, and proximity data.
 * </p>
 * * @param id The unique identifier assigned by the NASA JPL system.
 * 
 * @param name              The primary designation or common name of the
 *                          celestial body.
 * @param estDiameterMin    Minimum estimated diameter in kilometers.
 * @param estDiameterMax    Maximum estimated diameter in kilometers.
 * @param relativeVelocity  Velocity relative to Earth in km/h at the point of
 *                          closest approach.
 * @param missDistance      The distance between the asteroid and Earth at its
 *                          closest point (km).
 * @param absoluteMagnitude The intrinsic luminosity (H) of the asteroid.
 * @param approachDate      The date of the asteroid's closest approach to
 *                          Earth.
 */
public record AsteroidData(
                int id,

                String name,

                @JsonProperty("est_diameter_min") double estDiameterMin,

                @JsonProperty("est_diameter_max") double estDiameterMax,

                @JsonProperty("relative_velocity") double relativeVelocity,

                @JsonProperty("miss_distance") double missDistance,

                @JsonProperty("absolute_magnitude") double absoluteMagnitude,

                @JsonProperty("approach_date") LocalDate approachDate) {

        /**
         * Key used to navigate the NASA JSON structure for kilometer-based
         * measurements.
         */
        private static final String STD_MEASUREMENT_UNIT = "kilometers";

        /**
         * Key used to navigate the NASA JSON structure for velocity in km/h.
         */
        private static final String STD_VELOCITY = "kilometers_per_hour";

        /**
         * Factory method to transform a raw {@link JsonNode} from the NASA API into an
         * {@code AsteroidData} record.
         * *
         * <p>
         * This method handles the deep path navigation required by the NASA API
         * response structure,
         * specifically accessing the first element of the {@code close_approach_data}
         * array.
         * </p>
         * * @param asteroid The {@link JsonNode} containing raw asteroid attributes.
         * 
         * @return A populated {@code AsteroidData} instance.
         * @throws NullPointerException if the required JSON paths do not exist.
         */
        public static AsteroidData fromEntity(JsonNode asteroid) {

                // Extract and parse the approach date from the first entry in
                // close_approach_data
                String dateStr = asteroid.path("close_approach_data")
                                .get(0)
                                .path("close_approach_date")
                                .asString();
                LocalDate date = LocalDate.parse(dateStr);

                return new AsteroidData(
                                asteroid.path("id").asInt(),
                                asteroid.path("name").asString(),
                                // Navigate: estimated_diameter -> kilometers -> min
                                asteroid.path("estimated_diameter")
                                                .path(STD_MEASUREMENT_UNIT)
                                                .path("estimated_diameter_min")
                                                .asDouble(),
                                // Navigate: estimated_diameter -> kilometers -> max
                                asteroid.path("estimated_diameter")
                                                .path(STD_MEASUREMENT_UNIT)
                                                .path("estimated_diameter_max")
                                                .asDouble(),
                                // Navigate: close_approach_data[0] -> relative_velocity -> kilometers_per_hour
                                asteroid.path("close_approach_data")
                                                .get(0)
                                                .path("relative_velocity")
                                                .path(STD_VELOCITY)
                                                .asDouble(),
                                // Navigate: close_approach_data[0] -> miss_distance -> kilometers
                                asteroid.path("close_approach_data")
                                                .get(0)
                                                .path("miss_distance")
                                                .path(STD_MEASUREMENT_UNIT)
                                                .asDouble(),
                                // Map the absolute_magnitude_h field
                                asteroid.path("absolute_magnitude_h").asDouble(),
                                date);
        }
}