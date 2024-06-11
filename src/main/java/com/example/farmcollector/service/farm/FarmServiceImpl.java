package com.example.farmcollector.service.farm;

import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.util.FarmMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;
    private final FarmMapper farmMapper;


    public FarmServiceImpl(FarmRepository farmRepository, FarmerRepository farmerRepository, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmerRepository = farmerRepository;
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


    public FarmDTO addFarmerToFarm(Long farmId, Long farmerId) {
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new FarmDataNotFoundException("Farm not found with ths id"));
        Farmer farmer = farmerRepository.findById(farmerId).orElseThrow(() -> new FarmDataNotFoundException("Farmer not found with this id"));

        List<Farmer> farmerList = farm.getFarmers();
        farmerList.add(farmer);
        farm.setFarmers(farmerList);

        farmer.setFarm(farm);

        return farmMapper.convertFarmEntityToDto(farmRepository.save(farm));
    }
}
