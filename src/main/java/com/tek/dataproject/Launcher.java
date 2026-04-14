package com.tek.dataproject;

import javafx.application.Application;

public class Launcher {

    public static void main(String[] args) {
        Application.launch(AppLauncher.class, args);
    }
}

/*
Notes to implement on the front end----------------------

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Saved links from where I got the data:
https://eyes.nasa.gov/apps/exo/#/ - Link to eyes on exo planets
https://exoplanetarchive.ipac.caltech.edu/cgi-bin/TblView/nph-tblView?app=ExoTbls&config=STELLARHOSTS -nasa exoplanet archive
https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html - Oracle Docs (JDBC)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

Ways the data was measured:

Transit method — measuring how much starlight dims when a planet passes in front of its star. Gives radius.
Radial velocity — measuring the wobble a planet causes in its star. Gives mass.
Direct imaging — actually photographing the planet. Rare but most accurate.
Microlensing — measuring light bending from gravity. Gives mass estimates.


Oh and on the front end when you look something up i want to make it so that it shows a link to the nasa site showing the planet

11:04 PM
That's a clean feature. NASA Exoplanet Archive has a page for every confirmed planet. The URL pattern is:

https://exoplanetarchive.ipac.caltech.edu/overview/{planet_name}
So for Kepler-1167 b it would be:


In JavaFX you open it with:

java
Desktop.getDesktop().browse(new URI("https://exoplanetarchive.ipac.caltech.edu/overview/" + planetName.replace(" ", "%20")));


EXOPLANETS:::::

id                   | integer                |           | not null | nextval('exoplanet_dataset_id_seq'::regclass)
 planet_name          | character varying(100) |           |          |
 host_star            | character varying(100) |           |          |
 n_stars              | integer                |           |          |
 numberplanets            | integer                |           |          |
 discovery_method     | character varying(100) |           |          |
 disc_year            | numeric(10,2)          |           |          |
 disc_facility        | character varying(100) |           |          |
 orbital_period_days  | numeric(20,8)          |           |          |
 planet_radius_earth  | numeric(15,8)          |           |          |
 planet_mass_earth    | numeric(15,8)          |           |          |
 equilibrium_temp_k   | numeric(10,3)          |           |          |
 orbital_eccentricity | numeric(10,8)          |           |          |
 semi_major_axis_au   | numeric(15,8)          |           |          |
 star_temp_k          | numeric(10,3)          |           |          |
 star_radius_sun      | numeric(15,8)          |           |          |
 star_mass_sun        | numeric(15,8)          |           |          |
 star_age_gyr         | numeric(10,5)          |           |          |
 star_surface_gravity | numeric(10,5)          |           |          |
 star_metallicity     | numeric(10,5)          |           |          |
 dist_from_earth_pc   | numeric(15,8)          |           |          |
 star_vmag            | numeric(10,5)          |           |          |
 ra                   | numeric(15,8)          |           |          |
 dec                  | numeric(15,8)          |           |          |
 controversial_flag   | boolean                |           |          |
 planet_type          | character varying(50)  |           |          |
 habitable_zone_flag  | boolean                |           |          |
 multi_planet_system  | boolean                |           |          |
 is_recent_discovery  | boolean                |           |          |
 dist_category        | character varying(50)  |           |          |
 star_type            | character varying(50)  |           |          |
 orbital_period_cat   | character varying(50)  |           |          |
Indexes:
    "exoplanet_dataset_pkey" PRIMARY KEY, btree (id)










 */