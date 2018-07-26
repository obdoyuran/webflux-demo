package com.trendyol.webFluxDemo.service;

import com.trendyol.webFluxDemo.model.City;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityService {

    Flux<City> listCities();
    Mono<City> getCityById(Long id);
    Mono<City> saveCity(Mono<City> city);
    Mono<City> updateCity(Mono<City> city);
}
