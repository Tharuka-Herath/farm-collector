package com.example.farmcollector.service;

import com.example.farmcollector.dto.HarvestDTO;
import com.example.farmcollector.model.Harvest;
import com.example.farmcollector.repository.HarvestRepository;
import org.springframework.stereotype.Service;

@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;

    public HarvestServiceImpl(HarvestRepository harvestRepository) {
        this.harvestRepository = harvestRepository;
    }

    @Override
    public HarvestDTO saveHarvestDetails(HarvestDTO harvestDTO) {
        Harvest harvest = convertDTOToEntity(harvestDTO);

        return convertEntityToDTO(harvestRepository.save(harvest));
    }

    public Harvest convertDTOToEntity(HarvestDTO dto) {
        Harvest entity = new Harvest();

        entity.setHarvestId(dto.getHarvestId());
        entity.setFarmName(dto.getFarmName());
        entity.setSeason(dto.getSeason());
        entity.setCropName(dto.getCropName());
        entity.setActualAmount(dto.getActualAmount());

        return entity;
    }

    public HarvestDTO convertEntityToDTO(Harvest entity) {
        HarvestDTO dto = new HarvestDTO();

        dto.setHarvestId(entity.getHarvestId());
        dto.setFarmName(entity.getFarmName());
        dto.setSeason(entity.getSeason());
        dto.setCropName(entity.getCropName());
        dto.setActualAmount(entity.getActualAmount());

        return dto;
    }
}
