package com.bsac.meowa.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenMeteoResponse {
    private HourlyData hourly;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HourlyData {
        private List<String> time;
        
        @JsonProperty("temperature_2m")
        private List<Double> temperature;
        
        @JsonProperty("precipitation")
        private List<Double> precipitation;
        
        @JsonProperty("surface_pressure")
        private List<Double> surfacePressure;
        
        @JsonProperty("wind_speed_10m")
        private List<Double> windSpeed;
        
        @JsonProperty("wind_direction_10m")
        private List<Integer> windDirection;
        
        @JsonProperty("relative_humidity_2m")
        private List<Integer> relativeHumidity;

        @JsonProperty("apparent_temperature")
        private List<Double> apparentTemperature;

        @JsonProperty("weather_code")
        private List<Integer> weatherCode;
        
        // getters/setters
    }
    // getters/setters
}