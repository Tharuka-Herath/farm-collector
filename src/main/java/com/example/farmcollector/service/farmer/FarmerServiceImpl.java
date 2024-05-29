package com.example.farmcollector.service.farmer;

import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Farmer;
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

    public FarmerServiceImpl(FarmerRepository farmerRepository, FarmerMapper farmerMapper) {
        this.farmerRepository = farmerRepository;
        this.farmerMapper = farmerMapper;
    }

    @Override
    public FarmerDTO saveFarmer(FarmerDTO farmerDTO) {
        Farmer farmer = farmerMapper.convertFarmerDtoToEntity(farmerDTO);
        Farmer savedFarmer = farmerRepository.save(farmer);
        return farmerMapper.convertFarmerEntityToDto(savedFarmer);
    }

    @Override
    public List<FarmerDTO> getAllFarmers() {
        List<Farmer> farmersList = farmerRepository.findAll();
        return farmersList.stream().map(farmerMapper::convertFarmerEntityToDto).toList();
    }

    @Override
    public FarmerDTO getFarmerById(Long id) {
        Optional<Farmer> getFarmer = farmerRepository.findById(id);
        return farmerMapper.convertFarmerEntityToDto(getFarmer.orElseThrow(() -> new FarmDataNotFoundException("Farmer was not found with id " + id)));
    }

    @Transactional
    @Override
    public FarmerDTO updateFarmerById(Long id, FarmerDTO farmerDTO) {
        Optional<FarmerDTO> farmerToUpdate = Optional.ofNullable(getFarmerById(id));

        if(farmerToUpdate.isPresent()) {
           Farmer farmer = farmerMapper.convertFarmerDtoToEntity(farmerDTO);
           return farmerMapper.convertFarmerEntityToDto(farmerRepository.save(farmer));

        }  else {
            throw new FarmDataNotFoundException("No farmer with id " + id + " found.");
        }

    }

    @Override
    public void deleteFarmer(Long id) {
        if(farmerRepository.existsById(id)) {
            farmerRepository.deleteById(id);
        } else {
            throw new FarmDataNotFoundException("No farmer with id " + id + " found.");
        }
    }


}
