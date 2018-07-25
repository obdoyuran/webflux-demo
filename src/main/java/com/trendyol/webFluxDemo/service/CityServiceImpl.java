package com.trendyol.webFluxDemo.service;

import com.trendyol.webFluxDemo.model.City;
import com.trendyol.webFluxDemo.repository.CityRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class CityServiceImpl implements CityService {

    private final Scheduler scheduler;
    private final CityRepository cityRepository;

    public CityServiceImpl(@Qualifier("slowIOScheduler") Scheduler scheduler,
                           CityRepository cityRepository) {
        this.scheduler = scheduler;
        this.cityRepository = cityRepository;
    }

    @Override
    public Flux<City> listCities() {
        return Flux.fromIterable(cityRepository.findAll())
                .publishOn(scheduler);
    }

    @Override
    public Mono<City> getCityById(Long id) {
        return Mono.justOrEmpty(cityRepository.findById(id))
                .switchIfEmpty(Mono.error(new NotFoundException("City not found")))
                .publishOn(scheduler);
    }

    @Override
    public Mono<String> getCityNameById(Long id) {
        return getCityById(id)
                .filter(city -> !city.getCounties().isEmpty())
                .map(City::getName)
                .publishOn(scheduler);
    }

    @Override
    public Mono<City> saveCity(Mono<City> city) {
        return city
                .map(cityRepository::save)
                .publishOn(scheduler);
    }

    @Override
    public Mono<City> updateCity(Mono<City> newCity) {
        return newCity
                .zipWhen(c -> getCityById(c.getId()), this::convertUpdateRequest)
                .map(cityRepository::save)
                .publishOn(scheduler);
    }

    private City convertUpdateRequest(City request, City oldCity) {
        oldCity.setName(request.getName());
        return oldCity;
    }
}
