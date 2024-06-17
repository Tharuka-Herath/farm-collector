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
        cropDTO.setCropId(IdGenerator.generateCropId());
        cropDTO.getFarm().setFarmId(IdGenerator.generateFarmId());
        cropDTO.getFarmer().setFarmerId(IdGenerator.generateFarmerId());

        Crop crop = cropMapper.convertCropDtoToEntity(cropDTO);
        Farm farm = cropDTO.getFarm();
        crop.setFarm(farm);

        Farmer farmer = cropDTO.getFarmer();
        farmer.setFarm(farm);
        farm.getFarmers().add(farmer);
        crop.setFarmer(farmer);

        return cropMapper.convertCropEntityToDto(cropRepository.save(crop));
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
            Crop cropByIdEntity= cropById.get();
            return cropMapper.convertCropEntityToDto(cropByIdEntity);
        } else {
            throw new FarmDataNotFoundException("Crop not found for the given ID");
        }
    }

    /**
     * Updates a crop by its ID.
     *
     * @param cropId      the ID of the crop to be updated
     * @param cropDTO the CropDTO object with updated data
     * @return the updated CropDTO
     * @throws FarmDataNotFoundException if no crop is found with the given ID
     */
    @Override
    public CropDTO updateCropById(String cropId, CropDTO cropDTO) {
        Optional<Crop> updatedCrop = cropRepository.findCropByCropId(cropId);

        if (updatedCrop.isPresent()) {
            Long updatingId = updatedCrop.get().getId();
            Crop crop = cropMapper.convertCropDtoToEntity(cropDTO);
            crop.setId(updatingId);
            crop.setCropId(cropId);
            return cropMapper.convertCropEntityToDto(cropRepository.save(crop));
        } else {
            throw new FarmDataNotFoundException("No crop with ID " + cropId + " found.");
        }
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
            throw new FarmDataNotFoundException("Crop not found for the given ID");
        }
    }

    /**
     * Adds a farmer to a crop by updating the crop's farmer association in the database.
     *
     * @param farmerId The unique identifier of the farmer to be associated with the crop.
     * @param cropId   The unique identifier of the crop.
     * @return A CropDTO object representing the updated crop with the associated farmer.
     * @throws FarmDataNotFoundException if either the farmer with the specified ID or the crop with the specified ID is not found in the database.
     */
    @Override
    public CropDTO addFarmerToCrop(Long cropId, Long farmerId) {
        Farmer farmer = farmerRepository.findById(farmerId).orElseThrow(() -> new FarmDataNotFoundException("No farmer with the id"));
        Crop crop = cropRepository.findById(cropId).orElseThrow(() -> new FarmDataNotFoundException("No crop with this id"));
        crop.setFarmer(farmer);
        Crop saveUpdatedCrop = cropRepository.save(crop);
        return cropMapper.convertCropEntityToDto(saveUpdatedCrop);
    }

    /**
     * Adds a farm to a crop by updating the crop's farm association in the database.
     *
     * @param cropId The unique identifier of the crop.
     * @param farmId The unique identifier of the farm to be associated with the crop.
     * @return A CropDTO object representing the updated crop with the associated farm.
     * @throws FarmDataNotFoundException if either the farm with the specified ID or the crop with the specified ID is not found in the database.
     */
    @Override
    public CropDTO addFarmToCrop(Long cropId, Long farmId) {
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new FarmDataNotFoundException("No fam with this id"));
        Crop crop = cropRepository.findById(cropId).orElseThrow(() -> new FarmDataNotFoundException("No crop with this id"));
        crop.setFarm(farm);
        Crop addFarm = cropRepository.save(crop);
        return cropMapper.convertCropEntityToDto(addFarm);
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