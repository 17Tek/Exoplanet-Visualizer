package com.tek.dataproject.Services;

import com.tek.dataproject.Repositories.ExoplanetRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExoplanetService extends ExoplanetRepository
{
    public ExoplanetService(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
    }

    public Double findExoplanetDiameter(String planetName){
        Double radius = findRadiusByPlanetName(planetName);
        return radius*2;
    }


}
