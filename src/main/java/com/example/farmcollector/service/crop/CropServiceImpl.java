package com.example.farmcollector.service.crop;

import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Crop;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.CropRepository;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.util.CropMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing crops.
 */
@Service
public class CropServiceImpl implements CropService {
    private final CropRepository cropRepository;
    private final CropMapper cropMapper;
    private final FarmerRepository farmerRepository;

    /**
     * Constructs a new CropServiceImpl.
     *
     * @param cropRepository the repository used for CRUD operations on crops
     * @param cropMapper     the mapper used to convert between Crop entities and CropDTOs
     */
    public CropServiceImpl(CropRepository cropRepository, CropMapper cropMapper, FarmerRepository farmerRepository) {
        this.cropRepository = cropRepository;
        this.cropMapper = cropMapper;
        this.farmerRepository = farmerRepository;
    }

    /**
     * Saves a crop.
     *
     * @param cropDTO the CropDTO object to be saved
     * @return the saved CropDTO
     */
    @Override
    public CropDTO saveCrop(CropDTO cropDTO) {
        Crop crop = cropMapper.convertCropDtoToEntity(cropDTO);
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
     * @param id the ID of the crop
     * @return the CropDTO of the retrieved crop
     * @throws FarmDataNotFoundException if no crop is found with the given ID
     */
    @Override
    public CropDTO getCropById(Long id) {
        Optional<Crop> cropById = cropRepository.findById(id);
        if (cropById.isPresent()) {
            Crop crop = cropById.get();
            return cropMapper.convertCropEntityToDto(crop);
        } else {
            throw new FarmDataNotFoundException("Crop not found for the given ID");
        }
    }

    /**
     * Updates a crop by its ID.
     *
     * @param id      the ID of the crop to be updated
     * @param cropDTO the CropDTO object with updated data
     * @return the updated CropDTO
     * @throws FarmDataNotFoundException if no crop is found with the given ID
     */

    @Transactional
    @Override
    public CropDTO updateCropById(Long id, CropDTO cropDTO) {
        Optional<CropDTO> updatedCrop = Optional.ofNullable(getCropById(id));

        if (updatedCrop.isPresent()) {
            Crop crop = cropMapper.convertCropDtoToEntity(cropDTO);
            crop.setId(id);
            return cropMapper.convertCropEntityToDto(cropRepository.save(crop));
        } else {
            throw new FarmDataNotFoundException("No crop with ID " + id + " found.");
        }
    }

    /**
     * Deletes a crop by its ID.
     *
     * @param id the ID of the crop to be deleted
     * @throws FarmDataNotFoundException if no crop is found with the given ID
     */
    @Override
    public void deleteCrop(Long id) {
        Optional<Crop> crop = cropRepository.findById(id);
        if (crop.isPresent()) {
            cropRepository.deleteById(id);
        } else {
            throw new FarmDataNotFoundException("Crop not found for the given ID");
        }
    }

<<<<<<< Updated upstream

=======
    /**
     * Adds a farmer to a crop by updating the crop's farmer association in the database.
     *
     * @param farmerId The unique identifier of the farmer to be associated with the crop.
     * @param cropId   The unique identifier of the crop.
     * @return A CropDTO object representing the updated crop with the associated farmer.
     * @throws FarmDataNotFoundException if either the farmer with the specified ID or the crop with the specified ID is not found in the database.
     */
    @Override
    public CropDTO addFarmerToCrop(Long farmerId, Long cropId) {
        Farmer farmer = farmerRepository.findById(farmerId).orElseThrow(() -> new FarmDataNotFoundException("No farmer with the id"));
        Crop crop = cropRepository.findById(cropId).orElseThrow(() -> new FarmDataNotFoundException("No crop with this id"));
        crop.setFarmer(farmer);
        crop.setId(cropId);
        Crop addCrop = cropRepository.save(crop);
        return cropMapper.convertCropEntityToDto(addCrop);
    }
>>>>>>> Stashed changes
}
