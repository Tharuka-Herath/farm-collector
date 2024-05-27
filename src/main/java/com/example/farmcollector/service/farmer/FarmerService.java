package com.example.farmcollector.service.farmer;

import com.example.farmcollector.model.Farmer;

import java.util.List;

public interface FarmerService {
    List<Farmer> getAllFarmers();
    Farmer getFarmerById(Long id);
    Farmer saveFarmer(Farmer farmer);
    void deleteFarmer(Long id);

}
