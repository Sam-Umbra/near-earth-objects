package dev.umbra.space_radar_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object representing the prediction result from the ML model.
 * Contains the hazard assessment, confidence level, and processing status.
 *
 * @param id                     the unique identifier of the asteroid
 * @param name                   the designation or name of the asteroid
 * @param isPotentiallyHazardous indicates if the asteroid is classified as
 *                               dangerous
 * @param confidenceScore        the probability or reliability of the
 *                               prediction (0.0 to 1.0)
 * @param status                 the operational status of the prediction
 *                               request
 * @param estDiameterMin         the minimum estimated diameter of the asteroid
 * @param estDiameterMax         the maximum estimated diameter of the asteroid
 * @param relativeVelocity       the speed of the asteroid relative to Earth in
 *                               km/h
 * @param missDistance           the distance by which the asteroid will miss
 *                               Earth in kilometers
 * @param absoluteMagnitude      the absolute magnitude (H) of the asteroid,
 *                               indicating intrinsic luminosity
 */
public record PredictionResponse(

        /** Unique identifier assigned to the asteroid. */
        int id,

        /** The primary name or designation of the celestial object. */
        String name,

        /** Indicates if the asteroid is potentially hazardous. */
        @JsonProperty("is_hazardous") boolean isPotentiallyHazardous,

        /** The confidence score of the prediction (0.0 to 1.0). */
        @JsonProperty("confidence_score") double confidenceScore,

        /** The status of the prediction process (e.g., "SUCCESS", "FAILED"). */
        @JsonProperty("status") String status,

        /** Minimum estimated diameter in kilometers. */
        @JsonProperty("est_diameter_min") double estDiameterMin,

        /** Maximum estimated diameter in kilometers. */
        @JsonProperty("est_diameter_max") double estDiameterMax,

        /** Relative velocity in km/h. */
        @JsonProperty("relative_velocity") double relativeVelocity,

        /** Miss distance in kilometers. */
        @JsonProperty("miss_distance") double missDistance,

        /** Absolute magnitude (H). */
        @JsonProperty("absolute_magnitude") double absoluteMagnitude) {
}