package com.example.farmcollector.repository;

import com.example.farmcollector.model.Harvest;
import com.example.farmcollector.model.Plantation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantationRespository extends JpaRepository<Plantation,Long> {
}
