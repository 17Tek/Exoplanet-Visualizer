package com.tek.dataproject.Services;

import com.tek.dataproject.Repositories.ExoplanetRepository;
import com.tek.dataproject.TableRecord.Exoplanet;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExoplanetService {

    private final ExoplanetRepository repository;

    public ExoplanetService(ExoplanetRepository repository) {
        this.repository = repository;
    }

    public List<Exoplanet> findHostStar(String hostStar) {
        return repository.findByHostStar(hostStar);
    }

    public Exoplanet findRandomPlanet(){
         return repository.findRandomPlanet();
    }

    public String processLinkForPlanet(Exoplanet exoplanet){
        String exoplanetName = exoplanet.planetName();
        return "https://science.nasa.gov/exoplanet-catalog/"+ exoplanetName.toLowerCase().replace(" ","-") +"/";
    }

    public List<Exoplanet> searchWithFilters(String selectedPlanetType, String selectedDiscoveryMethod, Boolean habitableOnly){
        return repository.findByFilters(selectedPlanetType, selectedDiscoveryMethod, habitableOnly);
    }


    //derives the time and distances for the bar chart, used the measurements commented below
    public Map<String, Double> travelTimes(Exoplanet planet){

        if (planet.distFromEarthPc() == null) return new LinkedHashMap<>();
        double distanceParsecs = planet.distFromEarthPc();

        double distanceLightYears = distanceParsecs * 3.26156;
        double distanceKm = distanceParsecs * 3.086e13;
        double secondsPerYear = 31557600;

        double newHorizonsSpeedKmPerSecond= 13.8;//km/s
        double voyagerSpeedKmPerSecond = 17;//km/s
        double yearsByVoyager1 = distanceKm / voyagerSpeedKmPerSecond /secondsPerYear;
        double yearsByNewHorizons = distanceKm / newHorizonsSpeedKmPerSecond / secondsPerYear;
        double yearsAtOnePercentLightSpeed = distanceLightYears * 100;

        Map<String, Double> barChartDisplayMap = new LinkedHashMap<>();
        barChartDisplayMap.put("New Horizons", yearsByNewHorizons);
        barChartDisplayMap.put("Voyager 1", yearsByVoyager1);
        barChartDisplayMap.put("At Light Speed", distanceLightYears);
        barChartDisplayMap.put("1% Light Speed", yearsAtOnePercentLightSpeed);

        return barChartDisplayMap;

        /* Conversion factors for one parsec
        Seconds in a year = 31557600

        1 parsec ≈ 1 pc≈3.086×10 ^ 13km

        3.26156 light-years (ly)
        30.86 trillion kilometers (km)
        19.17 trillion miles (miles)
        206,265 Astronomical Units (AU)

        Voyager 1 — 17 km/s
        New Horizons — 13.8 km/s
        10% Light Speed — 29,979 km/s
        Light Speed — 299,792 km/s
     */
    }
}
