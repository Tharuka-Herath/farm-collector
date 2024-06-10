package com.example.farmcollector.service.crop;

import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.model.Crop;

import java.util.List;

public interface CropService {
    CropDTO saveCrop(CropDTO cropDTO);

    List<CropDTO> getAllCrops();

    CropDTO getCropById(Long id);

    CropDTO updateCropById(Long id, CropDTO cropDTO);

    void deleteCrop(Long id);

    CropDTO addFarmerToCrop(Long cropId, Long farmerId);

    CropDTO addFarmToCrop(Long cropId, Long farmerId);
}
