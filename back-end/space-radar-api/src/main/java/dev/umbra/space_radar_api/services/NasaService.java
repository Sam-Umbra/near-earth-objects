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
 * Service responsible for interacting with the NASA NeoWS API.
 * Fetches a 7-day window (3 days past + today + 3 days ahead) so both
 * historical close-approaches and upcoming ones are included.
 */
@Service
public class NasaService {

    /** NASA NeoWS allows a maximum window of 7 days per request. */
    private static final int DAYS_BEFORE = 3;
    private static final int DAYS_AFTER = 3;

    private final RestClient restClient;

    @Value("${nasa_api_key}")
    private String apiKey;

    /** The window start date of the last successful fetch. */
    private LocalDate lastFetchWindowStart;

    /** Current position in the cached asteroid list. */
    private int currentIndex;

    /** Cached list of asteroids for the current window. */
    private List<AsteroidData> asteroidList = new ArrayList<>();

    public NasaService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Retrieves the next asteroid from the cache.
     * Re-fetches when the list is exhausted or the calendar day has changed
     * (which shifts the 7-day window forward).
     *
     * @return the next AsteroidData, or null if none are available
     */
    public AsteroidData getNextAsteroid() {
        LocalDate windowStart = LocalDate.now().minusDays(DAYS_BEFORE);

        boolean windowShifted = !windowStart.equals(lastFetchWindowStart);
        boolean listExhausted = asteroidList.isEmpty() || currentIndex >= asteroidList.size();

        if (listExhausted || windowShifted) {
            LocalDate windowEnd = LocalDate.now().plusDays(DAYS_AFTER);
            asteroidList = fetchNasaApi(windowStart, windowEnd);
            currentIndex = 0;
            lastFetchWindowStart = windowStart;
        }

        if (asteroidList.isEmpty())
            return null;
        return asteroidList.get(currentIndex++);
    }

    /**
     * Performs the HTTP request to NASA NeoWS for the given date range.
     * Maximum allowed range is 7 days.
     *
     * @param startDate window start (inclusive)
     * @param endDate   window end (inclusive)
     * @return list of parsed AsteroidData, empty on error
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
            System.err.println("[NasaService] Error fetching NASA API: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Parses the raw NASA JSON response into AsteroidData DTOs.
     *
     * @param data raw JsonNode from the API
     * @return list of mapped AsteroidData objects
     */
    private List<AsteroidData> cleanNasaData(JsonNode data) {
        List<AsteroidData> list = new ArrayList<>();

        if (data == null || !data.has("near_earth_objects"))
            return list;

        data.path("near_earth_objects").properties().forEach(entry -> {
            JsonNode asteroidsForDay = entry.getValue();
            if (asteroidsForDay.isArray()) {
                for (JsonNode asteroid : asteroidsForDay) {
                    list.add(AsteroidData.fromEntity(asteroid));
                }
            }
        });

        return list;
    }
}