package com.example.farmcollector.service;

import com.example.farmcollector.dto.PlantationDTO;
import com.example.farmcollector.model.Plantation;
import com.example.farmcollector.repository.PlantationRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PlantationServiceImpl implements PlantationService {
    PlantationRespository plantationRepository;

    public PlantationServiceImpl(PlantationRespository plantationRepository) {
        this.plantationRepository = plantationRepository;
    }

   @Override
   public PlantationDTO savePlantationDetails(PlantationDTO plantationDTO){
        Plantation plantation = convertToEntity(plantationDTO);
        return convertToDTO(plantationRepository.save(plantation));
   }




    public PlantationDTO convertToDTO(Plantation plantation) {
        PlantationDTO dto = new PlantationDTO();
        dto.setFarm(plantation.getFarm());
        dto.setPlantedArea(plantation.getPlantedArea());
        dto.setSeason(plantation.getSeason());
        dto.setCropName(plantation.getCropName());
        dto.setExpectedAmount(plantation.getExpectedAmount());
        return dto;
    }

    public Plantation convertToEntity(PlantationDTO dto) {
        Plantation plantation = new Plantation();
        plantation.setFarm(dto.getFarm());
        plantation.setPlantedArea(dto.getPlantedArea());
        plantation.setSeason(dto.getSeason());
        plantation.setCropName(dto.getCropName());
        plantation.setExpectedAmount(dto.getExpectedAmount());

        return plantation;
    }
}
