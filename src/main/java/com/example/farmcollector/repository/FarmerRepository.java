package com.example.farmcollector.repository;

import com.example.farmcollector.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findFarmerByFarmerId(String farmerId);
    void deleteFarmerByFarmerId(String farmerId);
    boolean existsFarmerByFarmerId(String farmerId);
}
