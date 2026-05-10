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

    String SQL = null;
    RowMapper<Exoplanet> rowMapper = new DataClassRowMapper<>(Exoplanet.class);
    private final JdbcTemplate jdbcTemplate; //This is the JDBC template wrapper it helps connect to the database and extract the data using the SQL strings to do so
    public ExoplanetRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Exoplanet> findByFilters(String selectedPlanetType, String selectedDiscoveryMethod, Boolean habitableOnly){
        //It checks for if the value is null and if it is it will skip over that search value and move on to the next, It requires 2 of each variable because it checks if its null first and then what its actual value is
        //The cast tells postgres what kind of data the parameter is in that corresponding slot
        SQL = "SELECT * FROM exoplanet_dataset WHERE (CAST(? AS TEXT) IS NULL OR planet_type = ?) AND (CAST(? AS TEXT) IS NULL OR discovery_method = ?) AND (CAST(? AS BOOLEAN) IS NULL OR habitable_zone_flag = ?)";
        return jdbcTemplate.query(SQL, rowMapper, selectedPlanetType, selectedPlanetType, selectedDiscoveryMethod, selectedDiscoveryMethod, habitableOnly, habitableOnly);
    }


    public List<Exoplanet> findByHostStar(String hostStar){
        SQL = "SELECT * FROM exoplanet_dataset WHERE host_star = ?";
        return jdbcTemplate.query(SQL, rowMapper, hostStar);
    }

    public Exoplanet findRandomPlanet(){ //This orders the rows randomly and then limit one just picks the first one after the random order
        SQL = "SELECT * FROM exoplanet_dataset ORDER BY RANDOM() LIMIT 1";
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