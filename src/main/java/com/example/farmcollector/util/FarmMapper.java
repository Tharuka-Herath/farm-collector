package com.example.farmcollector.util;

import com.example.farmcollector.api.request.FarmRequest;
import com.example.farmcollector.api.response.FarmResponse;
import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.model.Farm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FarmMapper {


    public FarmDTO convertFarmEntityToDto(Farm entity) {
        FarmDTO dto = new FarmDTO();

        dto.setFarmName(entity.getFarmName());
        dto.setLocation(entity.getLocation());
        dto.setFarmArea(entity.getFarmArea());
        return dto;
    }

    public Farm convertFarmDtoToEntity(FarmDTO dto) {
        Farm entity = new Farm();

        entity.setFarmName(dto.getFarmName());
        entity.setLocation(dto.getLocation());
        entity.setFarmArea(dto.getFarmArea());
        return entity;
    }

    public FarmDTO convertFarmRequestToDto(FarmRequest request) {
        FarmDTO dto = new FarmDTO();

        dto.setFarmName(request.getFarmName());
        dto.setLocation(request.getLocation());
        dto.setFarmArea(request.getFarmArea());
        return dto;
    }

    public FarmResponse convertDtoToResponse(FarmDTO dto) {
        FarmResponse response = new FarmResponse();

        response.setFarmName(dto.getFarmName());
        response.setLocation(dto.getLocation());
        response.setFarmArea(dto.getFarmArea());
        return response;
    }


    /**
     * Converts a list of FarmDTO to a list of FarmResponse.
     *
     * @param farmDtoList The list of FarmDTO to convert.
     * @return The list of converted FarmResponse.
     */
    public List<FarmResponse> convertDtoListToResponseList(List<FarmDTO> farmDtoList) {
        return farmDtoList.stream()
                .map(this::convertDtoToResponse)
                .toList();
    }
}
