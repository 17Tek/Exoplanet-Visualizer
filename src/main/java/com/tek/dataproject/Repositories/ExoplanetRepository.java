package com.tek.dataproject.Repositories;

import com.tek.dataproject.TableRecord.Exoplanet;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;


//Contains all the SQL queries
//Purposely not using JPA so that I can manually write the SQL

@Repository
public class ExoplanetRepository
{
    RowMapper<Exoplanet> rowMapper = new DataClassRowMapper<>(Exoplanet.class);
    private final JdbcTemplate jdbcTemplate; //This is the JDBC template wrapper it helps connect to the database and extract the data using the SQL strings to do so
    public ExoplanetRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    //TODO get all planets within a distinct system with the same host stars


    //TODO get if system is multiplanet


    //TODO planet find radius
    public Double findRadiusByPlanetName(String planetName)
    {
        String SQL = "SELECT planet_radius_earth FROM exoplanet_dataset WHERE planet_name = ?";
        return jdbcTemplate.queryForObject(SQL, Double.class, planetName);
    }













    //Retrieves a distinct list of the all the possible host stars in the database
    public List<String> findAllDistinctHostStars() {
        String SQL = "SELECT DISTINCT host_star FROM exoplanet_dataset ORDER BY host_star ASC";
        return jdbcTemplate.queryForList(SQL, String.class);
    }

    //Create a list of all data from the exoplanets
    public List<Exoplanet> findAllExoplanets(){
        String SQL = "Select * FROM exoplanet_dataset";
        return jdbcTemplate.query(SQL, rowMapper);
    }


}

/*
List<Exoplanet> (many rows, many cols)query(sql, rowMapper)

Exoplanet (one row, many cols)queryForObject(sql, rowMapper, params...)

List<String> (many rows, one col)queryForList(sql, String.class)

String or int (one row, one col)queryForObject(sql, String.class, params...)

Nothing (INSERT/UPDATE/DELETE)update(sql, params...)
 */