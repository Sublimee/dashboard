package com.dashboard.service;

import com.dashboard.domain.City;
import com.dashboard.repository.CityRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Flux<City> getAllCities() {
        return cityRepository.findAll();
    }

    public Mono<City> geCityById(String id) {
        return cityRepository.findById(id);
    }
}