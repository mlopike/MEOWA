package com.bsac.meowa.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bsac.meowa.dto.OpenMeteoResponse;
import com.bsac.meowa.model.MeteoMetric;
import com.bsac.meowa.repo.MeteoMetricRepository;

@Service
public class WeatherFetchService {

    private static final Logger log = LoggerFactory.getLogger(WeatherFetchService.class);

    private final WebClient webClient;
    private final MeteoMetricRepository repository;

    public WeatherFetchService(MeteoMetricRepository repository) {
        this.repository = repository;
        this.webClient = WebClient.builder()
            .baseUrl("https://api.open-meteo.com")
            .build();
    }

    public void fetchAndSave(Integer locationId, double lat, double lon) {
        log.info("Fetching weather for locationId={}, lat={}, lon={}", locationId, lat, lon);

        try {
            OpenMeteoResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/v1/forecast")
                    .queryParam("longitude", lon)
                    .queryParam("latitude", lat)
                    .queryParam("hourly",
    "temperature_2m,apparent_temperature,weather_code,precipitation," +
    "surface_pressure,wind_speed_10m,wind_direction_10m,relative_humidity_2m")
                    .queryParam("forecast_days", 1)
                    .build())
                .retrieve()
                .bodyToMono(OpenMeteoResponse.class)
                .block();

            log.info("Response received, hourly records: {}", response.getHourly().getTime().size());

            List<MeteoMetric> metrics = new ArrayList<>();
            var hourly = response.getHourly();

            for (int i = 0; i < hourly.getTime().size(); i++) {
                MeteoMetric m = new MeteoMetric();
                m.setLocationId(locationId);
                m.setMeasureT(LocalDateTime.parse(hourly.getTime().get(i)));
                m.setTemperature(BigDecimal.valueOf(hourly.getTemperature().get(i)));
                m.setPrecipitation(BigDecimal.valueOf(hourly.getPrecipitation().get(i)));
                m.setSurfacePressure(BigDecimal.valueOf(hourly.getSurfacePressure().get(i)));
                m.setWindSpeed(BigDecimal.valueOf(hourly.getWindSpeed().get(i)));
                m.setWindDirection(degreesToCompass(hourly.getWindDirection().get(i)));
                m.setRelativeHumidity(hourly.getRelativeHumidity().get(i));
                m.setAddT(LocalDateTime.now());
                m.setApparentTemperature(BigDecimal.valueOf(hourly.getApparentTemperature().get(i)));
                m.setWeatherCode(hourly.getWeatherCode().get(i));
                metrics.add(m);
            }

            metrics.forEach(repository::insertIgnore);
            log.info("Saved {} records for locationId={}", metrics.size(), locationId);

        } catch (Exception e) {
            log.error("Error fetching weather for locationId={}", locationId, e);
        }
    }

    private String degreesToCompass(int degrees) {
        String[] dirs = {"С","СВ","В","ЮВ","Ю","ЮЗ","З","СЗ"};
        return dirs[(int)Math.round(degrees / 45.0) % 8];
    }
}