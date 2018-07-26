package com.trendyol.webFluxDemo.service;

import com.trendyol.webFluxDemo.model.County;
import reactor.core.publisher.Mono;

public interface CountyService {

    Mono<County> saveCounty(Mono<County> county);
}
