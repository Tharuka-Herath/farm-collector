package com.example.farmcollector.repository;

import com.example.farmcollector.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {

}
