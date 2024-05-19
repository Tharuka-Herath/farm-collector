package com.example.farmcollector.repository;

import com.example.farmcollector.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop, Long> {
}
