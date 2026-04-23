package com.tek.dataproject.Services;

import com.tek.dataproject.Repositories.SolarSystemPlanetRepository;
import com.tek.dataproject.TableRecord.Exoplanet;
import com.tek.dataproject.TableRecord.SolarSystemPlanet;
import org.springframework.stereotype.Service;

@Service
public class SolarSystemPlanetService
{
    private final SolarSystemPlanetRepository repository;

    public SolarSystemPlanetService(SolarSystemPlanetRepository repository) {
        this.repository = repository;
    }


    public SolarSystemPlanet compare(Exoplanet exoplanet){
        return repository.comparePlanetsResult(exoplanet);
    }

}
