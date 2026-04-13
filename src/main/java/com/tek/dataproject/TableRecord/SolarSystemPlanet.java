package com.tek.dataproject.TableRecord;

public record SolarSystemPlanet(

        int id,
        String name,
        String type,
        String dominantColor,
        Double mass,
        Double radius,
        Double density,
        Double surfaceGravity,
        Integer moons,
        Boolean ringSystem,
        String surfaceCharacteristics,
        String mostAbundantElement,
        Double orbitalPeriodDays,
        Double meanTemperature,
        Double averageDistanceFromSun
)
{
}
