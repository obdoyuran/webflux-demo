package com.trendyol.webFluxDemo.service;

import com.trendyol.webFluxDemo.model.County;
import com.trendyol.webFluxDemo.repository.CountyRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class CountyServiceImpl implements CountyService {

    private final CountyRepository countyRepository;
    private final Scheduler scheduler;

    public CountyServiceImpl(CountyRepository countyRepository,
                             @Qualifier("slowIOScheduler") Scheduler scheduler) {
        this.countyRepository = countyRepository;
        this.scheduler = scheduler;
    }

    @Override
    public Mono<County> saveCounty(Mono<County> county) {
        return Mono.defer(() -> county)
                .publishOn(scheduler)
                .map(countyRepository::save);
    }
}
