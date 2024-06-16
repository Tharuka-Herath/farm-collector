package com.example.farmcollector.util;

import com.example.farmcollector.api.request.FarmerRequest;
import com.example.farmcollector.api.response.FarmerResponse;
import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.model.Farmer;
import org.springframework.stereotype.Component;

/**
 * Utility class for converting between Farmer entities, DTOs, and request/response objects.
 */
@Component
public class FarmerMapper {

    /**
     * Converts a FarmerRequest to a FarmerDTO.
     *
     * @param request the FarmerRequest to convert.
     * @return the converted FarmerDTO.
     */
    public FarmerDTO convertFarmerRequestToDto(FarmerRequest request) {
        FarmerDTO dto = new FarmerDTO();

        dto.setFarmerName(request.getFarmerName());
        dto.setFarm(request.getFarm());

        return dto;
    }

    /**
     * Converts a FarmerDTO to a Farmer entity.
     *
     * @param dto the FarmerDTO to convert.
     * @return the converted Farmer entity.
     */
    public Farmer convertFarmerDtoToEntity(FarmerDTO dto) {
        Farmer entity = new Farmer();

        entity.setFarmerId(dto.getFarmerId());
        entity.setFarmerName(dto.getFarmerName());
        entity.setFarm(dto.getFarm());

        return entity;
    }

    /**
     * Converts a Farmer entity to a FarmerDTO.
     *
     * @param entity the Farmer entity to convert.
     * @return the converted FarmerDTO.
     */
    public FarmerDTO convertFarmerEntityToDto(Farmer entity) {
        FarmerDTO dto = new FarmerDTO();

        dto.setFarmerId(entity.getFarmerId());
        dto.setFarmerName(entity.getFarmerName());
        dto.setFarm(entity.getFarm());

        return dto;
    }

    /**
     * Converts a FarmerDTO to a FarmerResponse.
     *
     * @param dto the FarmerDTO to convert.
     * @return the converted FarmerResponse.
     */
    public FarmerResponse convertFarmerDtoToResponse(FarmerDTO dto) {
        FarmerResponse response = new FarmerResponse();

        response.setFarmerId(dto.getFarmerId());
        response.setFarmerName(dto.getFarmerName());
        response.setFarm(dto.getFarm());

        return response;
    }
}
