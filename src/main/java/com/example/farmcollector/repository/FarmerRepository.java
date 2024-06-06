package com.example.farmcollector.repository;

import com.example.farmcollector.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
}
