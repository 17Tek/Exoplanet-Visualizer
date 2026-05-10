package com.tek.dataproject.Repositories;

import com.tek.dataproject.TableRecord.Exoplanet;
import com.tek.dataproject.TableRecord.SolarSystemPlanet;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SolarSystemPlanetRepository
{
    RowMapper<SolarSystemPlanet> rowMapper = new DataClassRowMapper<>(SolarSystemPlanet.class);
    private final JdbcTemplate jdbcTemplate;
    public SolarSystemPlanetRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


}
