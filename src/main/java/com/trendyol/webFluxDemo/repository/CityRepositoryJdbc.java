package com.trendyol.webFluxDemo.repository;

import com.trendyol.webFluxDemo.model.City;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepositoryJdbc {

    private final JdbcTemplate jdbcTemplate;

    public CityRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<City> listCities() {
        return jdbcTemplate.query("select * from city", mapper);
    }

    private RowMapper<City> mapper = (resultSet, i) -> City.builder().name(resultSet.getString("name")).id(resultSet.getLong("id")).build();
}
