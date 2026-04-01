package dev.umbra.space_radar_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import tools.jackson.databind.JsonNode;

/**
 * Data Transfer Object representing relevant physical and orbital data of an asteroid.
 * This record is used to transport data from the NASA API to the ML prediction model.
 * <p>
 * Objeto de Transferência de Dados que representa dados físicos e orbitais relevantes de um asteroide.
 * Este record é usado para transportar dados da API da NASA para o modelo de previsão de ML.
 * 
 * @param estDiameterMin minimum estimated diameter in kilometers / diâmetro mínimo estimado em quilômetros
 * @param estDiameterMax maximum estimated diameter in kilometers / diâmetro máximo estimado em quilômetros
 * @param relativeVelocity velocity relative to Earth in km/h / velocidade relativa à Terra em km/h
 * @param missDistance distance by which the object misses Earth in kilometers / distância pela qual o objeto passa da Terra em quilômetros
 * @param absoluteMagnitude the intrinsic brightness of the celestial object / a magnitude absoluta (brilho intrínseco) do objeto celeste
 */
public record AsteroidData(
                /** Minimum estimated diameter. / Diâmetro mínimo estimado. */
                @JsonProperty("est_diameter_min") double estDiameterMin,

                /** Maximum estimated diameter. / Diâmetro máximo estimado. */
                @JsonProperty("est_diameter_max") double estDiameterMax,

                /** Relative velocity in km/h. / Velocidade relativa em km/h. */
                @JsonProperty("relative_velocity") double relativeVelocity,

                /** Miss distance in kilometers. / Distância de aproximação em quilômetros. */
                @JsonProperty("miss_distance") double missDistance,

                /** Absolute magnitude (H). / Magnitude absoluta (H). */
                @JsonProperty("absolute_magnitude") double absoluteMagnitude) {

        /** Standard unit for distance measurements. / Unidade padrão para medições de distância. */
        private static final String stdMeasurementUnit = "kilometers";
        
        /** Standard unit for velocity measurements. / Unidade padrão para medições de velocidade. */
        private static final String stdVelocity = "kilometers_per_hour";

        /**
         * Factory method that maps a raw JSON node from NASA's API to an AsteroidData instance.
         * <p>
         * Método factory que mapeia um nó JSON bruto da API da NASA para uma instância de AsteroidData.
         * 
         * @param asteroid the JsonNode containing raw asteroid data from NASA / o JsonNode contendo dados brutos do asteroide da NASA
         * @return a new AsteroidData instance populated with the extracted values / uma nova instância de AsteroidData preenchida com os valores extraídos
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