package com.trendyol.webFluxDemo.controller;

import com.trendyol.webFluxDemo.model.City;
import com.trendyol.webFluxDemo.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public Flux<City> listCities() {
        log.info("Thread {}:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
        return cityService.listCities();
    }

    @GetMapping("/{id}")
    public Mono<City> getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @PostMapping
    public Mono<City> saveCity(@RequestBody Mono<City> city) {
        return cityService.saveCity(city);
    }

    @PutMapping
    public Mono<City> updateCity(@RequestBody Mono<City> city) {
        return cityService.updateCity(city);
    }
}
