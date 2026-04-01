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
 * <p>
 * Serviço responsável por interagir com a API NeoWS (Near Earth Object Web Service) da NASA.
 * Ele busca, armazena em cache e fornece dados de asteroides para uma data específica.
 */
@Service
public class NasaService {

    private final RestClient restClient;

    @Value("${nasa_api_key}")
    private String apiKey;

    /** The date of the last successful data fetch. / A data da última busca de dados bem-sucedida. */
    private LocalDate lastFetchDate;
    
    /** The current position in the asteroid list. / A posição atual na lista de asteroides. */
    private int currentIndex;

    /** Cached list of asteroids for the current date. / Lista em cache de asteroides para a data atual. */
    private List<AsteroidData> asteroidList = new ArrayList<>();

    /**
     * Constructs a new NasaService with the required RestClient.
     * <p>
     * Constrói um novo NasaService com o RestClient necessário.
     * 
     * @param restClient the client used to make HTTP requests / o cliente usado para fazer requisições HTTP
     */
    public NasaService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Retrieves the next asteroid from the list. If the list is empty or the date has changed, 
     * it fetches new data from NASA.
     * <p>
     * Recupera o próximo asteroide da lista. Se a lista estiver vazia ou a data tiver mudado,
     * ele busca novos dados da NASA.
     * 
     * @return the next AsteroidData object, or null if none are available / o próximo objeto AsteroidData, ou null se nenhum estiver disponível
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
     * <p>
     * Realiza a requisição HTTP para a API da NASA para buscar dados de asteroides para um intervalo de datas.
     * 
     * @param startDate the start date for the search / a data de início para a busca
     * @param endDate the end date for the search / a data de término para a busca
     * @return a list of processed AsteroidData / uma lista de AsteroidData processados
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
     * <p>
     * Analisa a resposta JSON bruta da NASA em uma lista de DTOs AsteroidData.
     * 
     * @param data the raw JsonNode from the API / o JsonNode bruto da API
     * @return a list of mapped AsteroidData objects / uma lista de objetos AsteroidData mapeados
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
                    // Chamada correta do seu método factory
                    list.add(AsteroidData.fromEntity(asteroid));
                }
            }
        });

        return list;
    }

}
