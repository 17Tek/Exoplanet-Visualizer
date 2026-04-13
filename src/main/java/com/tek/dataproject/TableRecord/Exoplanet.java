package com.tek.dataproject.TableRecord;

public record Exoplanet(
        int id,
        String planetName,
        String hostStar,
        Integer nStars,
        Integer nPlanets,
        String discoveryMethod,
        Integer discYear,
        String discFacility,
        Double orbitalPeriodDays,
        Double planetRadiusEarth,
        Double planetMassEarth,
        Double equilibriumTempK,
        Double orbitalEccentricity,
        Double semiMajorAxisAu,
        Double starTempK,
        Double starRadiusSun,
        Double starMassSun,
        Double starAgeGyr,
        Double starSurfaceGravity,
        Double starMetallicity,
        Double distFromEarthPc,
        Double starVmag,
        Double ra,
        Double dec,
        Boolean controversialFlag,
        String planetType,
        Boolean habitableZoneFlag,
        Boolean multiPlanetSystem,
        Boolean isRecentDiscovery,
        String distCategory,
        String starType,
        String orbitalPeriodCat
) {}

//EXOPLANETS:::::
//
//id                   | integer                |           | not null | nextval('exoplanet_dataset_id_seq'::regclass)
//planet_name          | character varying(100) |           |          |
//host_star            | character varying(100) |           |          |
//n_stars              | integer                |           |          |
//numberplanets            | integer                |           |          |
//discovery_method     | character varying(100) |           |          |
//disc_year            | numeric(10,2)          |           |          |
//disc_facility        | character varying(100) |           |          |
//orbital_period_days  | numeric(20,8)          |           |          |

//planet_radius_earth  | numeric(15,8)          |           |          |
//planet_mass_earth    | numeric(15,8)          |           |          |
//equilibrium_temp_k   | numeric(10,3)          |           |          |
//orbital_eccentricity | numeric(10,8)          |           |          |
//semi_major_axis_au   | numeric(15,8)          |           |          |
//star_temp_k          | numeric(10,3)          |           |          |
//star_radius_sun      | numeric(15,8)          |           |          |
//star_mass_sun        | numeric(15,8)          |           |          |

//star_age_gyr         | numeric(10,5)          |           |          |
//star_surface_gravity | numeric(10,5)          |           |          |
//star_metallicity     | numeric(10,5)          |           |          |
//dist_from_earth_pc   | numeric(15,8)          |           |          |
//star_vmag            | numeric(10,5)          |           |          |
//ra                   | numeric(15,8)          |           |          |
//dec                  | numeric(15,8)          |           |          |
//controversial_flag   | boolean                |           |          |
//planet_type          | character varying(50)  |           |          |
//habitable_zone_flag  | boolean                |           |          |
//multi_planet_system  | boolean                |           |          |
//is_recent_discovery  | boolean                |           |          |
//dist_category        | character varying(50)  |           |          |
//star_type            | character varying(50)  |           |          |
//orbital_period_cat   | character varying(50)  |           |          |
//Indexes:
//        "exoplanet_dataset_pk
