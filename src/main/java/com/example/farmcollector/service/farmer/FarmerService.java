package com.example.farmcollector.service.farmer;

import com.example.farmcollector.dto.FarmerDTO;

import java.util.List;

public interface FarmerService {
    FarmerDTO saveFarmer(FarmerDTO farmerDTO);

    List<FarmerDTO> getAllFarmers();

    FarmerDTO getFarmerById(String farmerId);

    FarmerDTO updateFarmerById(String farmerId, FarmerDTO farmerDTO);

    void deleteFarmerById(String farmerId);

}
