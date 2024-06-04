package com.example.farmcollector.service.farm;

import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Crop;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.CropRepository;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.util.FarmMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;
    private final CropRepository cropRepository;
    private final FarmMapper farmMapper;


    public FarmServiceImpl(FarmRepository farmRepository, FarmerRepository farmerRepository, CropRepository cropRepository, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmerRepository = farmerRepository;
        this.cropRepository = cropRepository;
        this.farmMapper = farmMapper;

    }

    /**
     * Saves a new farm in the database.
     *
     * @param farmDTO The DTO representing the farm to be saved.
     * @return The saved farm as a DTO.
     */
    @Override
    public FarmDTO saveFarm(FarmDTO farmDTO) {
        Farm savedFarm = farmMapper.convertFarmDtoToEntity(farmDTO);
        return farmMapper.convertFarmEntityToDto(farmRepository.save(savedFarm));
    }

    /**
     * Updates a farm with the given ID.
     *
     * @param id      The ID of the farm to update.
     * @param farmDTO The DTO containing the updated farm data.
     * @return An Optional containing the updated farm as a DTO, or an empty Optional if the farm was not found.
     */
    @Transactional
    @Override
    public Optional<FarmDTO> updateFarm(Long id, FarmDTO farmDTO) {
        Optional<Farm> optionalFarm = farmRepository.findById(id);

        if (optionalFarm.isPresent()) {
            // Convert DTO to entity and set the ID to ensure the correct entity is updated
            Farm farmToUpdate = farmMapper.convertFarmDtoToEntity(farmDTO);
            farmToUpdate.setId(id);

            // Save the updated entity
            Farm updatedFarm = farmRepository.save(farmToUpdate);

            // Convert the saved entity back to DTO
            FarmDTO updatedFarmDTO = farmMapper.convertFarmEntityToDto(updatedFarm);

            return Optional.of(updatedFarmDTO);
        } else {
            return Optional.empty(); // Handle the case where the farm with the given ID does not exist
        }
    }

    /**
     * Retrieves all farms from the database.
     *
     * @return A list of all farms as DTOs.
     */
    @Override
    public List<FarmDTO> getAllFarms() {
        List<Farm> farms = farmRepository.findAll();
        return farms.stream()
                .map(farmMapper::convertFarmEntityToDto)
                .toList();
    }

    /**
     * Retrieves a farm by its ID.
     *
     * @param id The ID of the farm to retrieve.
     * @return The farm as a DTO.
     * @throws FarmDataNotFoundException If no farm is found with the given ID.
     */
    @Override
    public FarmDTO getFarmById(Long id) {
        Optional<Farm> optionalFarm = farmRepository.findById(id);

        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            return farmMapper.convertFarmEntityToDto(farm);
        } else {
            // Handle the case when the farm with the given ID is not found
            throw new FarmDataNotFoundException("Farm not found with id: " + id);
        }
    }

    /**
     * Deletes a farm with the given ID.
     *
     * @param id The ID of the farm to delete.
     */
    @Override
    public void deleteFarm(Long id) {
        if (farmRepository.existsById(id)) {
            farmRepository.deleteById(id);
        } else {
            throw new FarmDataNotFoundException("No farm with id " + id + " found.");
        }
    }

    /**
     * Adds a farmer to an existing farm
     *
     * @param farmId   the ID of the farm to which the farmer will be added
     * @param farmerId the ID of the farmer to be added to the farm
     * @return the updated FarmDTO after the farmer has been added
     * @throws FarmDataNotFoundException if the farm or farmer with the specified IDs are not found
     */
    @Override
    public FarmDTO addFarmerToFarm(Long farmId, Long farmerId) {
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new FarmDataNotFoundException("Farm not found with ths id"));
        Farmer farmer = farmerRepository.findById(farmerId).orElseThrow(() -> new FarmDataNotFoundException("Farmer not found with this id"));

        farm.getFarmers().add(farmer);
        farm.setId(farmId);

        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.convertFarmEntityToDto(savedFarm);
    }

    /**
     * Adds a specified crop to a specified farm.
     *
     * <p>Fetches the farm and crop entities using their respective IDs.
     * If either the farm or the crop is not found, it throws a {@code RuntimeException}.
     * The crop is then added to the farm's list of crops, and the farm is saved back to the repository.
     * Finally, the updated farm entity is converted to a {@code FarmDTO} and returned.</p>
     *
     * @param farmId the ID of the farm to which the crop will be added
     * @param cropId the ID of the crop to be added to the farm
     * @return a {@code FarmDTO} representing the updated farm with the new crop
     * @throws RuntimeException if either the farm or the crop is not found
     */
    @Override
    public FarmDTO addCropToFarm(Long farmId, Long cropId) {
        Crop crop = cropRepository.findById(cropId).orElseThrow(() -> new FarmDataNotFoundException("Crop not found with id: " + cropId));
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new FarmDataNotFoundException("Farm not found with id: " + farmId));
        farm.getCrops().add(crop);
        farm.setId(farmId);
        Farm cropSavedFarm = farmRepository.save(farm);
        return farmMapper.convertFarmEntityToDto(cropSavedFarm);
    }
}
