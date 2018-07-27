package com.trendyol.webFluxDemo.service;

import com.trendyol.webFluxDemo.model.City;
import com.trendyol.webFluxDemo.repository.CityRepository;
import com.trendyol.webFluxDemo.repository.CityRepositoryJdbc;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Slf4j
@Service
public class CityServiceImpl implements CityService {

    private final Scheduler scheduler;
    private final CityRepository cityRepository;
    private final CityServiceTransactional cityServiceTransactional;
    private final CityRepositoryJdbc cityRepositoryJdbc;

    public CityServiceImpl(@Qualifier("slowIOScheduler") Scheduler scheduler,
                           CityRepository cityRepository,
                           CityServiceTransactional cityServiceTransactional,
                           CityRepositoryJdbc cityRepositoryJdbc) {
        this.scheduler = scheduler;
        this.cityRepository = cityRepository;
        this.cityServiceTransactional = cityServiceTransactional;
        this.cityRepositoryJdbc = cityRepositoryJdbc;
    }

    @Override
    public Flux<City> listCities() {
        log.info("Thread {}:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
        return Flux.defer(() -> Flux.fromIterable(cityRepository.findAll()))
                .subscribeOn(scheduler)
                .publishOn(scheduler)
                .switchIfEmpty(Mono.error(new NotFoundException("City not found")));
    }

    @Override
    public Mono<City> getCityById(Long id) {
        return Mono.defer(() -> Mono.justOrEmpty(cityRepository.findById(id)))
                .subscribeOn(scheduler)
                .publishOn(scheduler)
                .switchIfEmpty(Mono.error(new NotFoundException("City not found")));
    }

    @Override
    public Mono<City> saveCity(Mono<City> city) {
        return city
                .map(cityRepository::save)
                .subscribeOn(scheduler)
                .publishOn(scheduler);
    }

    @Override
    public Mono<City> updateCity(Mono<City> newCity) {
        log.info("Thread {}:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
        return newCity
                .subscribeOn(scheduler)
                .publishOn(scheduler)
                .zipWhen(c -> getCityById(c.getId()), this::convertUpdateRequest)
                .map(cityRepository::save);
    }

    private City convertUpdateRequest(City request, City oldCity) {
        log.info("Thread {}:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
        oldCity.setName(request.getName());
        return oldCity;
    }
}
