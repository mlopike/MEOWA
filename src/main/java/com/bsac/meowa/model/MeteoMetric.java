package com.bsac.meowa.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data; 

    @Data
    @Entity
@Table(name = "Meteo_metrics")
public class MeteoMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer measureId;

    @Column(name = "location_id", nullable = false)
    private Integer locationId;

    private BigDecimal temperature;
    private BigDecimal precipitation;
    private BigDecimal surfacePressure;
    private BigDecimal windSpeed;

    @Column(length = 2)
    private String windDirection; // конвертируем градусы → CHAR(2)

    private Integer relativeHumidity;

    @Column(name = "apparent_temperature", precision = 3, scale = 1)
    private BigDecimal apparentTemperature;

    @Column(name = "weather_code")
    private Integer weatherCode;

    @Column(name = "measure_t") 
    private LocalDateTime measureT;

    @Column(name = "add_t") 
    private LocalDateTime addT;

    // getters/setters
}

