package com.example.farmcollector.util;

import com.example.farmcollector.api.request.FarmDataRequest;
import com.example.farmcollector.api.response.FarmDataResponse;
import com.example.farmcollector.dto.FarmDataDTO;

public class FarmDataMapper {
    public static FarmDataDTO convertRequestToDto(FarmDataRequest farmDataRequest) {
        FarmDataDTO farmDataDTO = new FarmDataDTO();

        farmDataDTO.setFarmer(farmDataRequest.getFarmer());
        farmDataDTO.setFarms(farmDataRequest.getFarms());
        farmDataDTO.setCrops(farmDataRequest.getCrops());

        return farmDataDTO;
    }

    public static FarmDataResponse convertDtoToResponse(FarmDataDTO farmDataDTO) {
        FarmDataResponse farmDataResponse = new FarmDataResponse();

        farmDataResponse.setFarmer(farmDataDTO.getFarmer());
        farmDataResponse.setFarms(farmDataDTO.getFarms());
        farmDataResponse.setCrops(farmDataDTO.getCrops());

        return farmDataResponse;
    }

}
