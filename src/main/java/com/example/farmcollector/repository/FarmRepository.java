package com.example.farmcollector.repository;

import com.example.farmcollector.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    // Retrieve farmers assigned for a specific farm
    @Query("SELECT f FROM Farm f WHERE Farm.farmName=:farmName" )
    List<Farm> findFarmByFarmId(@Param("farmName") String farmName);
}
