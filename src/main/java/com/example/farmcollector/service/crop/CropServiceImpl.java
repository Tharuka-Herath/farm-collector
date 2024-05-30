package com.example.farmcollector.service.crop;

import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Crop;
import com.example.farmcollector.repository.CropRepository;
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

    /**
     * Constructs a new CropServiceImpl.
     *
     * @param cropRepository the repository used for CRUD operations on crops
     * @param cropMapper     the mapper used to convert between Crop entities and CropDTOs
     */
    public CropServiceImpl(CropRepository cropRepository, CropMapper cropMapper) {
        this.cropRepository = cropRepository;
        this.cropMapper = cropMapper;
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
}
