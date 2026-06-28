package com.bsac.meowa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bsac.meowa.model.MeteoMetric;

public interface MeteoMetricRepository extends JpaRepository<MeteoMetric, Integer> {

    @Transactional
    @Modifying
    @Query(value = """
    INSERT IGNORE INTO `meteo_metrics`
    (location_id, temperature, apparent_temperature, weather_code,
     precipitation, surface_pressure, wind_speed, wind_direction, 
     relative_humidity, measure_t, add_t)
    VALUES
    (:#{#m.locationId}, :#{#m.temperature}, :#{#m.apparentTemperature}, 
     :#{#m.weatherCode}, :#{#m.precipitation}, :#{#m.surfacePressure}, 
     :#{#m.windSpeed}, :#{#m.windDirection}, :#{#m.relativeHumidity}, 
     :#{#m.measureT}, :#{#m.addT})
    """, nativeQuery = true)
void insertIgnore(@Param("m") MeteoMetric m);
List<MeteoMetric> findByLocationIdOrderByMeasureTAsc(Long locationId);
}