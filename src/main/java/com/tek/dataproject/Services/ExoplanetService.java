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

    //derives the time and distances for the bar chart, used the measurements commented below
    public Map<String, Double> travelTimes(Exoplanet planet){

        if (planet.distFromEarthPc() == null) return new LinkedHashMap<>();
        double distPC = planet.distFromEarthPc();

        double distLightYears = distPC * 3.26156;
        double distKm = distPC * 3.086e13;
        double secondsYear = 31557600;
        double speedNewHorizons= 13.8;//km/s
        double speedVoyager = 17;//km/s
        double voyagerYears = distKm / speedVoyager /secondsYear;
        double newHorizonsYears = distKm / speedNewHorizons / secondsYear;
        double onePercentLightYears = distLightYears * 100;

        Map<String, Double> barChartDisplayMap = new LinkedHashMap<>();
        barChartDisplayMap.put("New Horizons", newHorizonsYears);
        barChartDisplayMap.put("Voyager1", voyagerYears);
        barChartDisplayMap.put("Light years", distLightYears);
        barChartDisplayMap.put("1% of light speed", onePercentLightYears);

        return barChartDisplayMap;



        /* Conversion factors for one parsec
        Seconds in a year = 31557600

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
