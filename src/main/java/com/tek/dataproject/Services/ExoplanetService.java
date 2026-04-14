package com.tek.dataproject.Services;

import com.tek.dataproject.Repositories.ExoplanetRepository;
import com.tek.dataproject.TableRecord.Exoplanet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExoplanetService {

    private final ExoplanetRepository repository;

    public ExoplanetService(ExoplanetRepository repository) {
        this.repository = repository;
    }

    public List<Exoplanet> getSystemFor(String hostStar) {
        return repository.findByHostStar(hostStar);
    }

    public List<Exoplanet> findHostStar(String hostStar) {
        return repository.findByHostStar(hostStar);
    }

    public Exoplanet findRandomPlanet(){
         return repository.findRandomPlanet();
    }





}
