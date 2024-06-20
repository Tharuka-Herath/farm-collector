package com.example.farmcollector.repository;

import com.example.farmcollector.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    Optional<Farm> findFarmByFarmId(String farmId);

    void deleteFarmByFarmId(String farmId);

    boolean existsFarmByFarmId(String farmId);

    boolean existsFarmByFarmNameAndLocation(String farmId, String location);
}
