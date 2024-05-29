package com.example.farmcollector.service.farmer;

import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.util.FarmerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Farmer savedFarmer = farmerMapper.convertFarmerDtoToEntity(farmerDTO);
        return farmerMapper.convertFarmerEntityToDto(savedFarmer);
    }

    @Override
    public List<FarmerDTO> getAllFarmers() {
        List<Farmer> farmersList = farmerRepository.findAll();
        return farmersList.stream().map(farmerMapper::convertFarmerEntityToDto).toList();
    }

    @Override
    public FarmerDTO getFarmerById(Long id) {
        return null;
    }

    @Override
    public void deleteFarmer(Long id) {
        farmerRepository.deleteById(id);
    }
}
