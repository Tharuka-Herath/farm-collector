package com.example.farmcollector.service.crop;

import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.enums.Season;
import com.example.farmcollector.model.Crop;

import java.util.List;

public interface CropService {
    CropDTO saveCrop(CropDTO cropDTO);

    List<CropDTO> getAllCrops();

    CropDTO getCropById(String cropId);


    CropDTO updateCropById(String id, CropDTO cropDTO);

    void deleteCropByCropId(String cropId);

    CropDTO addFarmerToCrop(Long cropId, Long farmerId);

    CropDTO addFarmToCrop(Long cropId, Long farmerId);

    List<CropDTO> findAllByCropType(String cropType);

    Double findAverageYieldBySeasonAndYear(Season season, Integer year);

    List<Object[]> findCropsWithFarmLocationByCropType(String cropType);

    List<Object[]> findCropsByFarmNameAndSeason(String farmName, Season season);
}
