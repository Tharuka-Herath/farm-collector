package com.example.farmcollector.repository;

import com.example.farmcollector.enums.Season;
import com.example.farmcollector.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, Long> {

    // Find all crops of a specific type
    @Query("SELECT c FROM Crop c WHERE c.cropType = :cropType")
    List<Crop> findAllByCropType(@Param("cropType") String cropType);

    // Find the average yield of crops for a specific season and year
    @Query("SELECT AVG(c.actualAmount) FROM Crop c WHERE c.season = :season AND c.yieldYear = :year")
    Double findAverageYieldBySeasonAndYear(@Param("season") Season season, @Param("year") Integer year);

    // Find crops with a specific crop type along with farm location

    @Query("SELECT c.cropType, f.location FROM Crop c JOIN c.farm f WHERE c.cropType = :cropType")
    List<Object[]> findCropsWithFarmLocationByCropType(@Param("cropType") String cropType);




}
