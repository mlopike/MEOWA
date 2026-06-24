package com.bsac.meowa.controller;

import com.bsac.meowa.model.Location;
import com.bsac.meowa.model.MeteoMetric;
import com.bsac.meowa.repo.LocationRepository;
import com.bsac.meowa.repo.MeteoMetricRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin
public class WeatherController {

    private final MeteoMetricRepository meteoRepository;
    private final LocationRepository locationRepository;

    public WeatherController(MeteoMetricRepository meteoRepository,
                             LocationRepository locationRepository) {
        this.meteoRepository = meteoRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/{locationId}")
    public List<MeteoMetric> getWeather(@PathVariable Long locationId) {
        return meteoRepository.findByLocationIdOrderByMeasureTAsc(locationId);
    }

    @GetMapping("/locations")
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }
}