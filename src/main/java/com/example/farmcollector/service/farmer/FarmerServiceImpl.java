package com.example.farmcollector.service.farmer;

import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.FarmerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmerServiceImpl implements FarmerService {

    private final FarmerRepository farmerRepository;

    public FarmerServiceImpl(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    /**
     * @return 
     */
    @Override
    public List<Farmer> getAllFarmers() {
        return List.of();
    }

    /**
     * @param id 
     * @return
     */
    @Override
    public Farmer getFarmerById(Long id) {
        return null;
    }

    /**
     * @param farmer 
     * @return
     */
    @Override
    public Farmer saveFarmer(Farmer farmer) {
        return null;
    }

    /**
     * @param id 
     */
    @Override
    public void deleteFarmer(Long id) {

    }
}
