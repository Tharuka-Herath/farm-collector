package com.example.farmcollector.repository;

import com.example.farmcollector.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, Long> {

    // Find all crops of a specific type
    @Query("SELECT c FROM Crop c WHERE c.cropType = :cropType")
    List<Crop> findAllByCropType(@Param("cropType") String cropType);
}
