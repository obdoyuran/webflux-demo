package com.trendyol.webFluxDemo.service;

import com.trendyol.webFluxDemo.model.City;
import com.trendyol.webFluxDemo.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityServiceTransactional {

    private final CityRepository cityRepository;

    public CityServiceTransactional(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }
}
