package com.example.farmcollector.service.farm;

import com.example.farmcollector.dto.FarmDTO;

import java.util.List;

public interface FarmService {
    FarmDTO saveFarmAndFarmer(FarmDTO farmDTO);

    FarmDTO updateFarm(String farmId, FarmDTO farmDTO);

    List<FarmDTO> getAllFarms();

    FarmDTO getFarmById(String farmId);

    void deleteFarmById(String farmId);

    FarmDTO addFarmerToFarm(String farmId, String farmerId);
}
