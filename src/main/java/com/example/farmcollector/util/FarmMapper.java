package com.example.farmcollector.util;

import com.example.farmcollector.api.request.FarmRequest;
import com.example.farmcollector.api.response.FarmResponse;
import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.model.Farm;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utility class for converting between Farm entities, DTOs, and request/response objects.
 */
@Component
public class FarmMapper {

    /**
     * Converts a Farm entity to a FarmDTO.
     *
     * @param entity the Farm entity to convert.
     * @return the converted FarmDTO.
     */
    public FarmDTO convertFarmEntityToDto(Farm entity) {
        FarmDTO dto = new FarmDTO();

        dto.setFarmName(entity.getFarmName());
        dto.setLocation(entity.getLocation());
        dto.setFarmArea(entity.getFarmArea());
        dto.setFarmers(entity.getFarmers());
        return dto;
    }

    /**
     * Converts a FarmDTO to a Farm entity.
     *
     * @param dto the FarmDTO to convert.
     * @return the converted Farm entity.
     */
    public Farm convertFarmDtoToEntity(FarmDTO dto) {
        Farm entity = new Farm();

        entity.setFarmName(dto.getFarmName());
        entity.setLocation(dto.getLocation());
        entity.setFarmArea(dto.getFarmArea());
        entity.setFarmers(dto.getFarmers());
        return entity;
    }

    /**
     * Converts a FarmRequest to a FarmDTO.
     *
     * @param request the FarmRequest to convert.
     * @return the converted FarmDTO.
     */
    public FarmDTO convertFarmRequestToDto(FarmRequest request) {
        FarmDTO dto = new FarmDTO();

        dto.setFarmName(request.getFarmName());
        dto.setLocation(request.getLocation());
        dto.setFarmArea(request.getFarmArea());
        return dto;
    }

    /**
     * Converts a FarmDTO to a FarmResponse.
     *
     * @param dto the FarmDTO to convert.
     * @return the converted FarmResponse.
     */
    public FarmResponse convertDtoToResponse(FarmDTO dto) {
        FarmResponse response = new FarmResponse();

        response.setFarmName(dto.getFarmName());
        response.setLocation(dto.getLocation());
        response.setFarmArea(dto.getFarmArea());
        response.setFarmers(dto.getFarmers());
        return response;
    }

    /**
     * Converts a list of FarmDTO to a list of FarmResponse.
     *
     * @param farmDtoList the list of FarmDTO to convert.
     * @return the list of converted FarmResponse.
     */
    public List<FarmResponse> convertDtoListToResponseList(List<FarmDTO> farmDtoList) {
        return farmDtoList.stream()
                .map(this::convertDtoToResponse)
                .toList();
    }
}