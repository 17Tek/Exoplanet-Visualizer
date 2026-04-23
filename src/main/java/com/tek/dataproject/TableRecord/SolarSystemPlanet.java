package com.tek.dataproject.TableRecord;

public record SolarSystemPlanet(
        int id,
        String name,
        String type,
        String dominant_color,
        Double mass,
        Double radius,
        Double density,
        Double surface_gravity,
        Integer moons,
        Boolean ring_system,
        String surface_characteristics,
        String most_abundant_element,
        Double orbital_period_days,
        Double mean_temperature,
        Double average_distance_from_sun
) {}