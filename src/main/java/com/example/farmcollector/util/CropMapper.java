package com.example.farmcollector.util;

import com.example.farmcollector.api.request.CropRequest;
import com.example.farmcollector.api.response.CropResponse;
import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Crop;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utility class for converting between Crop entities, DTOs, and request/response objects.
 */
@Component
public class CropMapper {

    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;

    @Autowired
    public CropMapper(FarmRepository farmRepository, FarmerRepository farmerRepository) {
        this.farmRepository = farmRepository;
        this.farmerRepository = farmerRepository;
    }

    public Long convertFarmIdToDatabaseId(String farmId) {
        Farm farm = farmRepository.findFarmByFarmId(farmId).orElseThrow(() -> new FarmDataNotFoundException("No record with " + farmId + "to update"));
        return farm.getId();
    }

    public Long convertFarmerIdToDatabaseId(String farmerId) {
        Farmer farmer = farmerRepository.findFarmerByFarmerId(farmerId).orElseThrow(() -> new FarmDataNotFoundException("No farmer with id " + farmerId + " found."));
        return farmer.getId();
    }

    public String convertDatabaseIdToFarmId(long id) {
        Farm farm = farmRepository.findById(id).orElseThrow(() -> new FarmDataNotFoundException("No record with " + id + "to update"));
        return farm.getFarmId();
    }

    public String convertDatabaseIdToFarmerId(long id) {
        Farmer farmer = farmerRepository.findById(id).orElseThrow(() -> new FarmDataNotFoundException("No farmer with id " + id + " found."));
        return farmer.getFarmerId();
    }

    /**
     * Converts a CropRequest to a CropDTO.
     *
     * @param request the CropRequest to convert.
     * @return the converted CropDTO.
     */
    public CropDTO convertCropRequestToDto(CropRequest request) {
        CropDTO dto = new CropDTO();
        dto.setCropType(request.getCropType());
        dto.setSeason(request.getSeason());
        dto.setYieldYear(request.getYieldYear());
        dto.setExpectedAmount(request.getExpectedAmount());
        dto.setActualAmount(request.getActualAmount());
        dto.setFarmId(convertFarmIdToDatabaseId(request.getFarmId()));
        dto.setFarmerId(convertFarmerIdToDatabaseId(request.getFarmerId()));
        return dto;
    }


    /**
     * Converts a CropDTO to a Crop entity.
     *
     * @param dto the CropDTO to convert.
     * @return the converted Crop entity.
     */
    public Crop convertCropDtoToEntity(CropDTO dto) {
        Crop entity = new Crop();
        entity.setCropId(dto.getCropId());
        entity.setCropType(dto.getCropType());
        entity.setSeason(dto.getSeason());
        entity.setYieldYear(dto.getYieldYear());
        entity.setExpectedAmount(dto.getExpectedAmount());
        entity.setActualAmount(dto.getActualAmount());
        entity.setFarmId(dto.getFarmId());
        entity.setFarmerId(dto.getFarmerId());
        return entity;
    }

    /**
     * Converts a Crop entity to a CropDTO.
     *
     * @param entity the Crop entity to convert.
     * @return the converted CropDTO.
     */
    public CropDTO convertCropEntityToDto(Crop entity) {
        CropDTO dto = new CropDTO();
        dto.setCropId(entity.getCropId());
        dto.setCropType(entity.getCropType());
        dto.setSeason(entity.getSeason());
        dto.setYieldYear(entity.getYieldYear());
        dto.setExpectedAmount(entity.getExpectedAmount());
        dto.setActualAmount(entity.getActualAmount());
        dto.setFarmId(entity.getFarmId());
        dto.setFarmerId(entity.getFarmerId());
        return dto;
    }


    /**
     * Converts a CropDTO to a CropResponse.
     *
     * @param dto the CropDTO to convert.
     * @return the converted CropResponse.
     */
    public CropResponse convertDtoToResponse(CropDTO dto) {
        CropResponse response = new CropResponse();
        response.setCropId(dto.getCropId());
        response.setCropType(dto.getCropType());
        response.setSeason(dto.getSeason());
        response.setYieldYear(dto.getYieldYear());
        response.setExpectedAmount(dto.getExpectedAmount());
        response.setActualAmount(dto.getActualAmount());
        response.setFarmId(convertDatabaseIdToFarmId(dto.getFarmId()));
        response.setFarmerId(convertDatabaseIdToFarmerId(dto.getFarmerId()));
        return response;
    }

    /**
     * Converts a list of CropDTO to a list of CropResponse.
     *
     * @param cropDTOList the list of CropDTO to convert.
     * @return the list of converted CropResponse.
     */
    public List<CropResponse> convertDtoListToResponseList(List<CropDTO> cropDTOList) {
        return cropDTOList.stream().map(this::convertDtoToResponse).toList();
    }

    public List<CropDTO> convertCropEntityListToDtoList(List<Crop> entities) {
        return entities.stream().map(this::convertCropEntityToDto).toList();
    }
}