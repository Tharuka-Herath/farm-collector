package com.example.farmcollector.service.farmer;

import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.util.FarmerMapper;
import com.example.farmcollector.util.IdGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {

    private final FarmerRepository farmerRepository;
    private final FarmerMapper farmerMapper;

    /**
     * Saves a new farmer in the database.
     *
     * @param farmerDTO The DTO representing the farmer to be saved.
     * @return The saved farmer as a DTO.
     */
    @Override
    public FarmerDTO saveFarmer(FarmerDTO farmerDTO) {
        farmerDTO.setFarmerId(IdGenerator.generateFarmerId());

        Farmer farmer = farmerMapper.convertFarmerDtoToEntity(farmerDTO);
        Farmer savedFarmer = farmerRepository.save(farmer);

        return farmerMapper.convertFarmerEntityToDto(savedFarmer);
    }

    /**
     * Updates a farmer with the given ID.
     *
     * @param farmerId  The generated ID of the farmer to update.
     * @param farmerDTO The DTO containing the updated farmer data.
     * @return The updated farmer as a DTO.
     * @throws FarmDataNotFoundException if no farmer is found with the farmer ID.
     */
    @Override
    public FarmerDTO updateFarmerById(String farmerId, FarmerDTO farmerDTO) {
        Farmer farmer = farmerRepository.findFarmerByFarmerId(farmerId).orElseThrow(() -> new FarmDataNotFoundException("No farmer record with " + farmerId + " to update"));

        Farmer newFarmerEntity = farmerMapper.convertFarmerDtoToEntity(farmerDTO);
        newFarmerEntity.setId(farmer.getId());
        newFarmerEntity.setFarmerId(farmerId);
        newFarmerEntity.setFarm(farmer.getFarm());

        return farmerMapper.convertFarmerEntityToDto(farmerRepository.save(newFarmerEntity));
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
     * @param farmerId The generated ID of the farmer to retrieve.
     * @return The farmer as a DTO.
     * @throws FarmDataNotFoundException if no farmer is found with the given ID.
     */
    @Override
    public FarmerDTO getFarmerById(String farmerId) {
        Farmer getFarmer = farmerRepository.findFarmerByFarmerId(farmerId).orElseThrow(() -> new FarmDataNotFoundException("No farmer record found with id: " + farmerId));
        return farmerMapper.convertFarmerEntityToDto(getFarmer);
    }

    /**
     * Deletes a farmer with the given ID.
     *
     * @param farmerId The generated ID of the farmer to delete.
     * @throws FarmDataNotFoundException if no farmer is found with the farmer ID.
     */
    @Override
    public void deleteFarmerById(String farmerId) {
        if (farmerRepository.existsFarmerByFarmerId(farmerId)) {
            farmerRepository.deleteFarmerByFarmerId(farmerId);
        } else {
            throw new FarmDataNotFoundException("No farmer record found with id: " + farmerId);
        }
    }

}
