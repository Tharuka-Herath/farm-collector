package com.example.farmcollector.service.crop;

import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.enums.Season;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Crop;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.CropRepository;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.util.CropMapper;
import com.example.farmcollector.util.IdGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing crops.
 */
@Service
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {
    private final CropRepository cropRepository;
    private final CropMapper cropMapper;
    private final FarmerRepository farmerRepository;
    private final FarmRepository farmRepository;


    /**
     * Saves a crop.
     *
     * @param cropDTO the CropDTO object to be saved
     * @return the saved CropDTO
     */
    @Override
    public CropDTO saveCrop(CropDTO cropDTO) {
        boolean exists = cropRepository.existsByCropTypeAndSeasonAndYieldYearAndFarmIdAndFarmerId(cropDTO.getCropType(), cropDTO.getSeason(), cropDTO.getYieldYear(), cropDTO.getFarmId(), cropDTO.getFarmerId());

        if(exists) {
            throw new FarmDataNotFoundException("Duplicated record");
        }
        cropDTO.setCropId(IdGenerator.generateCropId());

        Farm farm = farmRepository.findById(cropDTO.getFarmId()).orElseThrow(() -> new FarmDataNotFoundException("No farm selected"));
        Farmer farmer = farmerRepository.findById(cropDTO.getFarmId()).orElseThrow(() -> new FarmDataNotFoundException("No farmer selected"));

        farm.getFarmers().add(farmer);
        farmer.setFarm(farm);

        Crop cropToSave = cropMapper.convertCropDtoToEntity(cropDTO);

        return cropMapper.convertCropEntityToDto(cropRepository.save(cropToSave));
    }

    /**
     * Updates a crop by its ID.
     *
     * @param cropId  the ID of the crop to be updated
     * @param cropDTO the CropDTO object with updated data
     * @return the updated CropDTO
     * @throws FarmDataNotFoundException if no crop is found with the given ID
     */
    @Override
    public CropDTO updateCropById(String cropId, CropDTO cropDTO) {
        Crop crop = cropRepository.findCropByCropId(cropId).orElseThrow(() -> new FarmDataNotFoundException("No crop record with " + cropId + " to update"));

        Long cropIdLong = crop.getId();
        Long farmId = cropDTO.getFarmId();
        Long farmerId = cropDTO.getFarmerId();

        Crop newCropEntity = cropMapper.convertCropDtoToEntity(cropDTO);
        newCropEntity.setId(cropIdLong);
        newCropEntity.setFarmId(farmId);
        newCropEntity.setFarmerId(farmerId);

        return cropMapper.convertCropEntityToDto(cropRepository.save(crop));

    }

    /**
     * Retrieves a crop by its ID.
     *
     * @param cropId the ID of the crop
     * @return the CropDTO of the retrieved crop
     * @throws FarmDataNotFoundException if no crop is found with the given ID
     */
    @Override
    public CropDTO getCropById(String cropId) {
        Optional<Crop> cropById = cropRepository.findCropByCropId(cropId);
        if (cropById.isPresent()) {
            Crop cropByIdEntity = cropById.get();
            return cropMapper.convertCropEntityToDto(cropByIdEntity);
        } else {
            throw new FarmDataNotFoundException("No crop record found with id: " + cropId);
        }
    }

    /**
     * Retrieves all crops.
     *
     * @return a list of CropDTOs
     */
    @Override
    public List<CropDTO> getAllCrops() {
        List<Crop> crops = cropRepository.findAll();
        return crops.stream().map(cropMapper::convertCropEntityToDto).toList();
    }


    /**
     * Deletes a crop by its ID.
     *
     * @param cropId the ID of the crop to be deleted
     * @throws FarmDataNotFoundException if no crop is found with the given ID
     */
    @Override
    @Transactional
    public void deleteCropByCropId(String cropId) {
        Optional<Crop> crop = cropRepository.findCropByCropId(cropId);
        if (crop.isPresent()) {
            cropRepository.deleteCropByCropId(cropId);
        } else {
            throw new FarmDataNotFoundException("No crop record found with id: " + cropId);
        }
    }


    /**
     * Finds all crops of a given crop type.
     *
     * @param cropType the type of crop to search for
     * @return a list of {@link CropDTO} objects representing the found crops
     */
    @Override
    public List<CropDTO> findAllByCropType(String cropType) {
        List<Crop> crops = cropRepository.findAllByCropType(cropType);
        return cropMapper.convertCropEntityListToDtoList(crops);
    }

    /**
     * Finds the average yield for a given season and year.
     *
     * @param season the season for which to calculate the average yield
     * @param year   the year for which to calculate the average yield
     * @return the average yield for the given season and year
     */
    @Override
    public Double findAverageYieldBySeasonAndYear(Season season, Integer year) {
        return cropRepository.findAverageYieldBySeasonAndYear(season, year);
    }

    @Override
    public List<Object[]> findCropsWithFarmLocationByCropType(String cropType) {
        return cropRepository.findCropsWithFarmLocationByCropType(cropType);
    }

    @Override
    public List<Object[]> findCropsByFarmNameAndSeason(String farmName, Season season) {
        return cropRepository.findCropsByFarmNameAndSeason(farmName, season);
    }

}