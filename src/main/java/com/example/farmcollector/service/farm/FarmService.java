package com.example.farmcollector.service.farm;

import com.example.farmcollector.dto.FarmDTO;

import java.util.List;
import java.util.Optional;

public interface FarmService {
    FarmDTO saveFarm(FarmDTO farmDTO);

    Optional<FarmDTO> updateFarm(String farmId, FarmDTO farmDTO);

    List<FarmDTO> getAllFarms();

    FarmDTO getFarmById(String farmId);

    void deleteFarmById(String farmId);

    FarmDTO addFarmerToFarm(String farmId, String farmerId);
}
