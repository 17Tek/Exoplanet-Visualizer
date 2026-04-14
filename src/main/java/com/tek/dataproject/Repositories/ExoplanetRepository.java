package com.tek.dataproject.Repositories;

import com.tek.dataproject.TableRecord.Exoplanet;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;


//Contains all the SQL queries
//Purposely not using JPA or creating a large object so that I can manually write the SQL

@Repository
public class ExoplanetRepository
{
    RowMapper<Exoplanet> rowMapper = new DataClassRowMapper<>(Exoplanet.class);
    private final JdbcTemplate jdbcTemplate; //This is the JDBC template wrapper it helps connect to the database and extract the data using the SQL strings to do so
    public ExoplanetRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Exoplanet> getPlanetDiscoveryInfo(String planetName){
        String SQL = "SELECT discovery_method, disc_year FROM exoplanet_dataset WHERE planet_name = ?";
        return jdbcTemplate.query(SQL, rowMapper, planetName);
    }

    //Todo get planet radius earth
    public Double findPlanetRadius(String planetName){
        String SQL = "SELECT planet_radius_earth FROM exoplanet_dataset WHERE planet_name = ?";
        return jdbcTemplate.queryForObject(SQL, Double.class, planetName);
    }

    //todo semi major axis au
    public Double findSemiMajorAxisAu(String planetName){
        String SQL = "SELECT semi_major_axis_au FROM exoplanet_dataset WHERE planet_name = ?";
        return jdbcTemplate.queryForObject(SQL, Double.class, planetName);
    }

    //todo star radius sun
    public Double findStarRadiusSun(String planetName){
        String SQL = "SELECT star_radius_sun FROM exoplanet_dataset WHERE planet_name = ?";
        return jdbcTemplate.queryForObject(SQL, Double.class, planetName);
    }

    //todo star temp k
    public Double findStarTempK(String planetName){
        String SQL = "SELECT star_temp_k FROM exoplanet_dataset WHERE planet_name = ?";
        return jdbcTemplate.queryForObject(SQL, Double.class, planetName);
    }

    //TODO planet find radius
    public Double findRadiusByPlanetName(String planetName)
    {
        String SQL = "SELECT planet_radius_earth FROM exoplanet_dataset WHERE planet_name = ?";
        return jdbcTemplate.queryForObject(SQL, Double.class, planetName);
    }

    //Todo find by host star
    public List<Exoplanet> findByHostStar(String hostStar){
        String SQL = "SELECT * FROM exoplanet_dataset WHERE host_star = ?";
        return jdbcTemplate.query(SQL, rowMapper, hostStar);
    }

    public Exoplanet findRandomPlanet(){ //This orders the rows randomly and then limit one just picks the first one after the random order
        String SQL = "SELECT * FROM exoplanet_dataset ORDER BY RANDOM() LIMIT 1";
        return jdbcTemplate.queryForObject(SQL, rowMapper);
    }




}

/*
List<Exoplanet> (many rows, many cols)query(sql, rowMapper)

Exoplanet (one row, many cols)queryForObject(sql, rowMapper, params...)

List<String> (many rows, one col)queryForList(sql, String.class)

String or int (one row, one col)queryForObject(sql, String.class, params...)

Nothing (INSERT/UPDATE/DELETE)update(sql, params...)
 */