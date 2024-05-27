package com.example.farmcollector.service.crop;

import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.model.Crop;
import com.example.farmcollector.repository.CropRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropServiceImpl implements CropService {
    private final CropRepository cropRepository;

    public CropServiceImpl(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    /**
     * @param cropDTO
     * @return
     */
    @Override
    public CropDTO saveCrop(CropDTO cropDTO) {
        Crop savedCrop = convertCropDtoToEntity(cropDTO);
        return convertCropEntityToDto(savedCrop);
    }

    /**
     * @return
     */
    @Override
    public List<CropDTO> getAllCrops() {
        return List.of();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CropDTO getCropById(Long id) {
        return null;
    }

    /**
     * @param id
     */
    @Override
    public void deleteCrop(Long id) {
        cropRepository.deleteById(id);
    }

    public Crop convertCropDtoToEntity(CropDTO dto) {
        Crop entity = new Crop();

        entity.setCropType(dto.getCropType());
        entity.setSeason(dto.getSeason());
        entity.setYieldYear(dto.getYieldYear());
        entity.setExpectedAmount(dto.getExpectedAmount());
        entity.setActualAmount(dto.getActualAmount());

        return entity;
    }

    public CropDTO convertCropEntityToDto(Crop entity) {
        CropDTO dto = new CropDTO();

        dto.setCropType(entity.getCropType());
        dto.setSeason(entity.getSeason());
        dto.setYieldYear(entity.getYieldYear());
        dto.setExpectedAmount(entity.getExpectedAmount());
        dto.setActualAmount(entity.getActualAmount());

        return dto;
    }


}
