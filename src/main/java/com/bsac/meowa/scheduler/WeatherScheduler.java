package com.bsac.meowa.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bsac.meowa.repo.LocationRepository;
import com.bsac.meowa.service.WeatherFetchService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherScheduler {

    private final WeatherFetchService service;
    private final LocationRepository locationRepository;

    @Scheduled(initialDelay = 0, fixedDelay = 3_600_000)
    public void fetchAll() {
        locationRepository.findAll().forEach(location ->
            service.fetchAndSave(
                location.getLocationId(),
                location.getLat().doubleValue(),
                location.getLon().doubleValue()
            )
        );
    }
}
