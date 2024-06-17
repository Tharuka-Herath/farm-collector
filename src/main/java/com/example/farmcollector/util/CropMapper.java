package com.example.farmcollector.util;

import com.example.farmcollector.api.request.CropRequest;
import com.example.farmcollector.api.response.CropResponse;
import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.model.Crop;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utility class for converting between Crop entities, DTOs, and request/response objects.
 */
@Component
public class CropMapper {
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
        entity.setFarm(dto.getFarm());
        entity.setFarmer(dto.getFarmer());
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
        dto.setFarm(entity.getFarm());
        dto.setFarmer(entity.getFarmer());
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
        response.setFarm(dto.getFarm());
        response.setFarmer(dto.getFarmer());
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
        return entities.stream()
                .map(this::convertCropEntityToDto)
                .toList();
    }
}