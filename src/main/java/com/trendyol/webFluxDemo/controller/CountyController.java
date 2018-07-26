package com.trendyol.webFluxDemo.controller;

import com.trendyol.webFluxDemo.model.County;
import com.trendyol.webFluxDemo.service.CountyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/county")
public class CountyController {

    private final CountyService countyService;

    public CountyController(CountyService countyService) {
        this.countyService = countyService;
    }

    @PostMapping
    public Mono<County> saveCounty(@RequestBody Mono<County> county) {
        return countyService.saveCounty(county);
    }
}
