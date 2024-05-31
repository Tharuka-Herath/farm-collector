package com.example.farmcollector.service.farm;


import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.dto.FarmDTO;

import java.util.List;
import java.util.Optional;

public interface FarmService {
    FarmDTO saveFarm(FarmDTO farmDTO);
    Optional<FarmDTO> updateFarm(Long id, FarmDTO farmDTO);
    List<FarmDTO> getAllFarms();
    FarmDTO getFarmById(Long id);
    void deleteFarm(Long id);
    FarmDTO addFarmerToFarm(Long farmId, Long farmerId);
    FarmDTO addCropToFarm(Long farmId,Long CropId);
}
