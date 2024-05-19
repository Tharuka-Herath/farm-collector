package com.example.farmcollector.service;

import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.dto.FarmDataDTO;
import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.model.Crop;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.CropRepository;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.repository.FarmerRepository;

public class FarmDataServiceImpl implements FarmDataService {


    private final FarmerRepository farmerRepository;
    private final FarmRepository farmRepository;
    private final CropRepository cropRepository;

    public FarmDataServiceImpl(FarmerRepository farmerRepository, FarmRepository farmRepository, CropRepository cropRepository) {
        this.farmerRepository = farmerRepository;
        this.farmRepository = farmRepository;
        this.cropRepository = cropRepository;
    }

    @Override
    public FarmDataDTO saveFarmData(FarmDataDTO farmDataDTO) {

        return null;
    }

    public Farmer convertFarmerDTOToEntity(FarmerDTO farmerDTO) {
        Farmer farmer = new Farmer();
        farmer.setFarmerName(farmerDTO.getFarmerName());
        return farmer;
    }

    private Farm convertFarmDTOToEntity(FarmDTO farmDTO) {
        Farm farm = new Farm();

        farm.setFarmName(farmDTO.getFarmName());
        farm.setFarmArea(farmDTO.getFarmArea());
        farm.setSeason(farmDTO.getSeason());
        farm.setYieldYear(farmDTO.getYieldYear());
        return farm;
    }

    private Crop convertCropDTOToEntity(CropDTO cropDTO) {
        Crop crop = new Crop();

        crop.setCropName(cropDTO.getCropName());
        crop.setActualAmount(cropDTO.getActualAmount());
        crop.setExpectedAmount(cropDTO.getExpectedAmount());
        return crop;
    }

    public FarmerDTO convertFarmerEntityToDTO(Farmer farmer) {
        FarmerDTO farmerDTO = new FarmerDTO();
        farmerDTO.setFarmerName(farmer.getFarmerName());
        return farmerDTO;
    }

    private FarmDTO convertFarmEntityToDTO(Farm farm) {
        FarmDTO farmDTO = new FarmDTO();

        farmDTO.setFarmName(farm.getFarmName());
        farmDTO.setFarmArea(farm.getFarmArea());
        farmDTO.setSeason(farm.getSeason());
        farmDTO.setYieldYear(farm.getYieldYear());
        return farmDTO;
    }

    private CropDTO convertCropEntityToDTO(Crop crop) {
        CropDTO cropDTO = new CropDTO();

        cropDTO.setCropName(crop.getCropName());
        cropDTO.setActualAmount(crop.getActualAmount());
        cropDTO.setExpectedAmount(crop.getExpectedAmount());
        return cropDTO;
    }
}
