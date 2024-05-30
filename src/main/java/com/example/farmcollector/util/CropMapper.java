package com.example.farmcollector.util;


import com.example.farmcollector.api.request.CropRequest;
import com.example.farmcollector.api.response.CropResponse;
import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.model.Crop;
import org.springframework.stereotype.Component;


import java.util.List;

@Component

public class CropMapper {

    public CropDTO convertCropEntityToDto(Crop entity){
        CropDTO dto = new CropDTO();
        dto.setCropId(entity.getCropId());
        dto.setCropType(entity.getCropType());
        dto.setSeason(entity.getSeason());
        dto.setYieldYear(entity.getYieldYear());
        dto.setExpectedAmount(entity.getExpectedAmount());
        dto.setActualAmount(entity.getActualAmount());
        return dto;
    }

    public Crop convertCropDtoToEntity(CropDTO dto){
        Crop crop = new Crop();
        crop.setCropId(dto.getCropId());
        crop.setCropType(dto.getCropType());
        crop.setSeason(dto.getSeason());
        crop.setYieldYear(dto.getYieldYear());
        crop.setExpectedAmount(dto.getExpectedAmount());
        return crop;
    }

    public CropDTO convertCropRequestToDto(CropRequest request){
        CropDTO dto = new CropDTO();
        dto.setCropType(request.getCropType());
        dto.setSeason(request.getSeason());
        dto.setYieldYear(request.getYieldYear());
        dto.setExpectedAmount(request.getExpectedAmount());
        dto.setActualAmount(request.getActualAmount());
        return dto;

    }

    public CropResponse convertDtoToResponse(CropDTO dto){
        CropResponse response = new CropResponse();
        response.setCropType(dto.getCropType());
        response.setSeason(dto.getSeason());
        response.setYieldYear(dto.getYieldYear());
        response.setExpectedAmount(dto.getExpectedAmount());
        response.setActualAmount(dto.getActualAmount());
        return response;
    }

    public List<CropResponse> convertDtoListToResponseList(List<CropDTO> cropDTOList){
        return  cropDTOList.stream().map(this::convertDtoToResponse).toList();
    }




}