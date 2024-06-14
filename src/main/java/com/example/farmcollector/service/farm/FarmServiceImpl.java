package com.example.farmcollector.service.farm;

import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.util.FarmMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;
    private final FarmMapper farmMapper;

    /**
     * Saves a new farm in the database.
     *
     * @param farmDTO The DTO representing the farm to be saved.
     * @return The saved farm as a DTO.
     */
    @Override
    public FarmDTO saveFarm(FarmDTO farmDTO) {
        Farm farmEntity = farmMapper.convertFarmDtoToEntity(farmDTO);
        Farm savedFarmEntity = farmRepository.save(farmEntity);
        return farmMapper.convertFarmEntityToDto(savedFarmEntity);
    }

    /**
     * Updates a farm with the generated farm ID.
     *
     * @param farmId  The generated ID of the farm to update.
     * @param farmDTO The DTO containing the updated farm data.
     * @return An Optional containing the updated farm as a DTO, or an empty Optional if the farm was not found.
     */
    @Transactional
    @Override
    public Optional<FarmDTO> updateFarm(String farmId, FarmDTO farmDTO) {
        Optional<Farm> optionalFarm = farmRepository.findFarmByFarmId(farmId);

        if (optionalFarm.isPresent()) {
            // Convert DTO to entity and set the ID to ensure the correct entity is updated
            Farm newFarmDataEntity = farmMapper.convertFarmDtoToEntity(farmDTO);
            newFarmDataEntity.setFarmId(farmId);
            newFarmDataEntity.setId(optionalFarm.get().getId());

            // Save the updated entity
            Farm updatedFarm = farmRepository.save(newFarmDataEntity);

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
        return farms.stream().map(farmMapper::convertFarmEntityToDto).toList();
    }

    /**
     * Retrieves a farm by its generated ID.
     *
     * @param farmId The generated ID of the farm to retrieve.
     * @return The farm as a DTO.
     * @throws FarmDataNotFoundException If no farm is found with the generated farm ID.
     */
    @Override
    public FarmDTO getFarmById(String farmId) {
        Optional<Farm> optionalFarm = farmRepository.findFarmByFarmId(farmId);

        if (optionalFarm.isPresent()) {
            Farm farm = optionalFarm.get();
            return farmMapper.convertFarmEntityToDto(farm);
        } else {
            // Handle the case when the farm with the farm ID is not found
            throw new FarmDataNotFoundException("Farm not found with id: " + farmId);
        }
    }

    /**
     * Deletes a farm with the given ID.
     *
     * @param farmId The ID of the farm to delete.
     */
    @Override
    public void deleteFarmById(String farmId) {

        if (farmRepository.existsFarmByFarmId(farmId)) {
            farmRepository.deleteFarmByFarmId(farmId);
        } else {
            throw new FarmDataNotFoundException("No farm with id " + farmId + "found");
        }
    }

    /**
     * Adds a farmer to a farm.
     *
     * @param farmId   The generated ID of the farm to which the farmer will be added.
     * @param farmerId The generated ID of the farmer to be added to the farm.
     * @return A FarmDTO representing the updated farm after adding the farmer.
     * @throws FarmDataNotFoundException if the farm or farmer with the farm ID is not found.
     */
    public FarmDTO addFarmerToFarm(String farmId, String farmerId) {
        Farm farm = farmRepository.findFarmByFarmId(farmId).orElseThrow(() -> new FarmDataNotFoundException("No farm with id " + farmId + "found"));
        Farmer farmer = farmerRepository.findFarmerByFarmerId(farmerId).orElseThrow(() -> new FarmDataNotFoundException("No farmer with id " + farmerId + "found"));

        List<Farmer> farmerList = farm.getFarmers();
        farmerList.add(farmer);
        farm.setFarmers(farmerList);

        farmer.setFarm(farm);

        return farmMapper.convertFarmEntityToDto(farmRepository.save(farm));
    }
}
