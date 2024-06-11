package com.example.farmcollector.service.farmer;

import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.util.FarmerMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmerServiceImpl implements FarmerService {

    private final FarmerRepository farmerRepository;
    private final FarmerMapper farmerMapper;
    private final FarmRepository farmRepository;

    public FarmerServiceImpl(FarmerRepository farmerRepository, FarmerMapper farmerMapper,FarmRepository farmRepository) {
        this.farmerRepository = farmerRepository;
        this.farmerMapper = farmerMapper;
        this.farmRepository = farmRepository;
    }

    /**
     * Saves a new farmer in the database.
     *
     * @param farmerDTO The DTO representing the farmer to be saved.
     * @return The saved farmer as a DTO.
     */
    @Override
    public FarmerDTO saveFarmer(FarmerDTO farmerDTO) {
        Farmer farmer = farmerMapper.convertFarmerDtoToEntity(farmerDTO);
        Farmer savedFarmer = farmerRepository.save(farmer);
        return farmerMapper.convertFarmerEntityToDto(savedFarmer);
    }

    /**
     * Updates a farmer with the given ID.
     *
     * @param id        The ID of the farmer to update.
     * @param farmerDTO The DTO containing the updated farmer data.
     * @return The updated farmer as a DTO.
     * @throws FarmDataNotFoundException if no farmer is found with the given ID.
     */
    @Transactional
    @Override
    public FarmerDTO updateFarmerById(Long id, FarmerDTO farmerDTO) {
        Optional<Farmer> farmerToUpdate = farmerRepository.findById(id);

        if (farmerToUpdate.isPresent()) {
            Farmer farmer = farmerMapper.convertFarmerDtoToEntity(farmerDTO);
            farmer.setId(id);
            return farmerMapper.convertFarmerEntityToDto(farmerRepository.save(farmer));

        } else {
            throw new FarmDataNotFoundException("No farmer with id " + id + " found.");
        }

    }

    /**
     * Retrieves all farmers from the database.
     *
     * @return A list of all farmers as DTOs.
     */
    @Override
    public List<FarmerDTO> getAllFarmers() {
        List<Farmer> farmersList = farmerRepository.findAll();
        return farmersList.stream().map(farmerMapper::convertFarmerEntityToDto).toList();
    }

    /**
     * Retrieves a farmer by its ID.
     *
     * @param id The ID of the farmer to retrieve.
     * @return The farmer as a DTO.
     * @throws FarmDataNotFoundException if no farmer is found with the given ID.
     */
    @Override
    public FarmerDTO getFarmerById(Long id) {
        Optional<Farmer> getFarmer = farmerRepository.findById(id);
        return farmerMapper.convertFarmerEntityToDto(getFarmer.orElseThrow(() -> new FarmDataNotFoundException("Farmer was not found with id " + id)));
    }

    /**
     * Deletes a farmer with the given ID.
     *
     * @param id The ID of the farmer to delete.
     * @throws FarmDataNotFoundException if no farmer is found with the given ID.
     */
    @Override
    public void deleteFarmer(Long id) {
        if (farmerRepository.existsById(id)) {
            farmerRepository.deleteById(id);
        } else {
            throw new FarmDataNotFoundException("No farmer with id " + id + " found.");
        }
    }

}
