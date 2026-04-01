package dev.umbra.space_radar_api.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import dev.umbra.space_radar_api.models.dtos.AsteroidData;
import tools.jackson.databind.JsonNode;

/**
 * Service responsible for interacting with the NASA NeoWS (Near Earth Object Web Service) API.
 * It fetches, caches, and provides asteroid data for a specific date.
 */
@Service
public class NasaService {

    private final RestClient restClient;

    @Value("${nasa_api_key}")
    private String apiKey;

    /** The date of the last successful data fetch. */
    private LocalDate lastFetchDate;
    
    /** The current position in the asteroid list. */
    private int currentIndex;

    /** Cached list of asteroids for the current date. */
    private List<AsteroidData> asteroidList = new ArrayList<>();

    /**
     * Constructs a new NasaService with the required RestClient.
     * 
     * @param restClient the client used to make HTTP requests
     */
    public NasaService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Retrieves the next asteroid from the list. If the list is empty or the date has changed, 
     * it fetches new data from NASA.
     * 
     * @return the next AsteroidData object, or null if none are available
     */
    public AsteroidData getNextAsteroid() {
        LocalDate today = LocalDate.now();

        if (asteroidList.isEmpty() || currentIndex >= asteroidList.size() || !today.equals(lastFetchDate)) {
            asteroidList = fetchNasaApi(today, today);
            currentIndex = 0;
            lastFetchDate = today;
        }

        if (asteroidList.isEmpty())
            return null;

        return asteroidList.get(currentIndex++);
    }

    /**
     * Performs the HTTP request to the NASA API to fetch asteroid data for a date range.
     * 
     * @param startDate the start date for the search
     * @param endDate the end date for the search
     * @return a list of processed AsteroidData
     */
    private List<AsteroidData> fetchNasaApi(LocalDate startDate, LocalDate endDate) {
        try {
            JsonNode rawData = restClient.get()
                    .uri("https://api.nasa.gov/neo/rest/v1/feed", uriBuilder -> uriBuilder
                            .queryParam("start_date", startDate.toString())
                            .queryParam("end_date", endDate.toString())
                            .queryParam("api_key", apiKey)
                            .build())
                    .retrieve()
                    .body(JsonNode.class);

            return cleanNasaData(rawData);

        } catch (RestClientException e) {
            System.err.println("Error fetching Nasa Api: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Parses the raw JSON response from NASA into a list of AsteroidData DTOs.
     * 
     * @param data the raw JsonNode from the API
     * @return a list of mapped AsteroidData objects
     */
    private List<AsteroidData> cleanNasaData(JsonNode data) {
        List<AsteroidData> list = new ArrayList<>();

        if (data == null || !data.has("near_earth_objects")) {
            return list;
        }

        JsonNode nearEarthObjects = data.path("near_earth_objects");

        nearEarthObjects.properties().forEach(entry -> {
            JsonNode asteroidsInDate = entry.getValue();

            if (asteroidsInDate.isArray()) {
                for (JsonNode asteroid : asteroidsInDate) {
                    list.add(AsteroidData.fromEntity(asteroid));
                }
            }
        });

        return list;
    }

}
