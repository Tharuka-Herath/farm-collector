package com.example.farmcollector.util;

import com.example.farmcollector.api.request.FarmerRequest;
import com.example.farmcollector.api.response.FarmerResponse;
import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.model.Farmer;
import org.springframework.stereotype.Component;

@Component
public class FarmerMapper {

    public FarmerDTO convertFarmerEntityToDto(Farmer entity) {
        FarmerDTO dto = new FarmerDTO();

        dto.setFarmerId(entity.getFarmerId());
        dto.setFarmerName(entity.getFarmerName());

        return dto;
    }

    public Farmer convertFarmerDtoToEntity(FarmerDTO dto) {
        Farmer entity = new Farmer();

        entity.setFarmerId(dto.getFarmerId());
        entity.setFarmerName(dto.getFarmerName());

        return entity;
    }

    public FarmerDTO convertFarmerRequestToDto(FarmerRequest request) {
        FarmerDTO dto = new FarmerDTO();

        dto.setFarmerId(request.getFarmerId());
        dto.setFarmerName(request.getFarmerName());

        return dto;
    }

    public FarmerResponse convertFarmerDtoToResponse(FarmerDTO dto) {
        FarmerResponse response = new FarmerResponse();

        response.setFarmerId(dto.getFarmerId());
        response.setFarmerName(dto.getFarmerName());

        return response;
    }
}
