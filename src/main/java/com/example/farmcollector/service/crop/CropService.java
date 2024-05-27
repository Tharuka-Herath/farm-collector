package com.example.farmcollector.service.crop;

import com.example.farmcollector.dto.CropDTO;

import java.util.List;

public interface CropService {
    CropDTO saveCrop(CropDTO cropDTO);
    List<CropDTO> getAllCrops();
    CropDTO getCropById(Long id);
    void deleteCrop(Long id);


}
