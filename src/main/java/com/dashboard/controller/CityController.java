package com.dashboard.controller;

import com.dashboard.domain.City;
import com.dashboard.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public Flux<City> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/{id}")
    public Mono<City> getCityById(@PathVariable String id) {
        return cityService.geCityById(id);
    }
}