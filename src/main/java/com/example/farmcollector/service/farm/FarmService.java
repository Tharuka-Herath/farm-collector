package com.example.farmcollector.service.farm;


import com.example.farmcollector.dto.FarmDTO;

import java.util.List;
import java.util.Optional;

public interface FarmService {
    boolean existsById(Long id);
    FarmDTO saveFarm(FarmDTO farmDTO);
    List<FarmDTO> getAllFarms();
    FarmDTO getFarmById(Long id);
    Optional<FarmDTO> updateFarm(Long id, FarmDTO farmDTO);
    void deleteFarm(Long id);
}
