package com.example.farmcollector.service;

import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.enums.Season;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Crop;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.CropRepository;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.service.crop.CropServiceImpl;
import com.example.farmcollector.util.CropMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CropServiceImplTest {

    @Mock
    private CropRepository cropRepository;

    @Mock
    private CropMapper cropMapper;

    @Mock
    private FarmerRepository farmerRepository;

    @Mock
    private FarmRepository farmRepository;

    @InjectMocks
    private CropServiceImpl cropService;

    private Crop crop;
    private CropDTO cropDTO;
    private Farmer farmer;
    private Farm farm;

    @BeforeEach
    void setUp() {
        farm = new Farm();
        farm.setId(1L);
        farm.setFarmName("Test Farm");

        farmer = new Farmer();
        farmer.setId(1L);
        farmer.setFarmerName("John Doe");

        crop = new Crop();
        crop.setId(1L);
        crop.setCropType("Wheat");
        crop.setSeason(Season.YALA);
        crop.setYieldYear(2023);
        crop.setExpectedAmount(100.0);
        crop.setActualAmount(90.0);
        crop.setFarmer(farmer);
        crop.setFarm(farm);

        cropDTO = new CropDTO();
        cropDTO.setCropType("Wheat");
        cropDTO.setSeason(Season.YALA);
        cropDTO.setYieldYear(2023);
        cropDTO.setExpectedAmount(100.0);
        cropDTO.setActualAmount(90.0);
        cropDTO.setFarmer(farmer);
        cropDTO.setFarm(farm);
    }

    @Test
    void saveCrop_success() {
        when(cropMapper.convertCropDtoToEntity(any(CropDTO.class))).thenReturn(crop);
        when(cropRepository.save(any(Crop.class))).thenReturn(crop);
        when(cropMapper.convertCropEntityToDto(any(Crop.class))).thenReturn(cropDTO);

        CropDTO result = cropService.saveCrop(cropDTO);

        assertNotNull(result);
        assertEquals(cropDTO.getCropType(), result.getCropType());
        verify(cropRepository, times(1)).save(any(Crop.class));
    }

    @Test
    void getAllCrops_success() {
        when(cropRepository.findAll()).thenReturn(List.of(crop));
        when(cropMapper.convertCropEntityToDto(any(Crop.class))).thenReturn(cropDTO);

        List<CropDTO> result = cropService.getAllCrops();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(cropRepository, times(1)).findAll();
    }

    @Test
    void getCropById_success() {
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));
        when(cropMapper.convertCropEntityToDto(any(Crop.class))).thenReturn(cropDTO);

        CropDTO result = cropService.getCropById(1L);

        assertNotNull(result);
        assertEquals(cropDTO.getCropType(), result.getCropType());
        verify(cropRepository, times(1)).findById(1L);
    }

    @Test
    void getCropById_notFound() {
        when(cropRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            cropService.getCropById(1L);
        });

        assertEquals("Crop not found for the given ID", exception.getMessage());
        verify(cropRepository, times(1)).findById(1L);
    }

    @Test
    void updateCropById_success() {
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));
        when(cropMapper.convertCropDtoToEntity(any(CropDTO.class))).thenReturn(crop);
        when(cropRepository.save(any(Crop.class))).thenReturn(crop);
        when(cropMapper.convertCropEntityToDto(any(Crop.class))).thenReturn(cropDTO);

        CropDTO result = cropService.updateCropById(1L, cropDTO);

        assertNotNull(result);
        assertEquals(cropDTO.getCropType(), result.getCropType());
        verify(cropRepository, times(1)).findById(1L);
        verify(cropRepository, times(1)).save(any(Crop.class));
    }

    @Test
    void updateCropById_notFound() {
        when(cropRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            cropService.updateCropById(1L, cropDTO);
        });

        assertEquals("No crop with ID 1 found.", exception.getMessage());
        verify(cropRepository, times(1)).findById(1L);
        verify(cropRepository, times(0)).save(any(Crop.class));
    }

    @Test
    void deleteCrop_success() {
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));

        cropService.deleteCrop(1L);

        verify(cropRepository, times(1)).findById(1L);
        verify(cropRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCrop_notFound() {
        when(cropRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            cropService.deleteCrop(1L);
        });

        assertEquals("Crop not found for the given ID", exception.getMessage());
        verify(cropRepository, times(1)).findById(1L);
        verify(cropRepository, times(0)).deleteById(1L);
    }

    @Test
    void addFarmerToCrop_success() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.of(farmer));
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));
        when(cropRepository.save(any(Crop.class))).thenReturn(crop);
        when(cropMapper.convertCropEntityToDto(any(Crop.class))).thenReturn(cropDTO);

        CropDTO result = cropService.addFarmerToCrop(1L, 1L);

        assertNotNull(result);
        verify(farmerRepository, times(1)).findById(1L);
        verify(cropRepository, times(1)).findById(1L);
        verify(cropRepository, times(1)).save(any(Crop.class));
    }

    @Test
    void addFarmerToCrop_farmerNotFound() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            cropService.addFarmerToCrop(1L, 1L);
        });

        assertEquals("No farmer with the id", exception.getMessage());
        verify(farmerRepository, times(1)).findById(1L);
        verify(cropRepository, times(0)).findById(1L);
        verify(cropRepository, times(0)).save(any(Crop.class));
    }

    @Test
    void addFarmerToCrop_cropNotFound() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.of(farmer));
        when(cropRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            cropService.addFarmerToCrop(1L, 1L);
        });

        assertEquals("No crop with this id", exception.getMessage());
        verify(farmerRepository, times(1)).findById(1L);
        verify(cropRepository, times(1)).findById(1L);
        verify(cropRepository, times(0)).save(any(Crop.class));
    }

    @Test
    void addFarmToCrop_success() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));
        when(cropRepository.save(any(Crop.class))).thenReturn(crop);
        when(cropMapper.convertCropEntityToDto(any(Crop.class))).thenReturn(cropDTO);

        CropDTO result = cropService.addFarmToCrop(1L, 1L);

        assertNotNull(result);
        verify(farmRepository, times(1)).findById(1L);
        verify(cropRepository, times(1)).findById(1L);
        verify(cropRepository, times(1)).save(any(Crop.class));
    }

    @Test
    void addFarmToCrop_farmNotFound() {
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            cropService.addFarmToCrop(1L, 1L);
        });

        assertEquals("No fam with this id", exception.getMessage());
        verify(farmRepository, times(1)).findById(1L);
        verify(cropRepository, times(0)).findById(1L);
        verify(cropRepository, times(0)).save(any(Crop.class));
    }

    @Test
    void addFarmToCrop_cropNotFound() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(cropRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            cropService.addFarmToCrop(1L, 1L);
        });

        assertEquals("No crop with this id", exception.getMessage());
        verify(farmRepository, times(1)).findById(1L);
        verify(cropRepository, times(1)).findById(1L);
        verify(cropRepository, times(0)).save(any(Crop.class));
    }

    @Test
    void findAllByCropType_success() {
        when(cropRepository.findAllByCropType("Wheat")).thenReturn(List.of(crop));
        when(cropMapper.convertCropEntityListToDtoList(any(List.class))).thenReturn(List.of(cropDTO));

        List<CropDTO> result = cropService.findAllByCropType("Wheat");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cropDTO.getCropType(), result.get(0).getCropType());
        verify(cropRepository, times(1)).findAllByCropType("Wheat");
    }

    @Test
    void findAverageYieldBySeasonAndYear_success() {
        when(cropRepository.findAverageYieldBySeasonAndYear(Season.YALA, 2023)).thenReturn(90.0);

        Double result = cropService.findAverageYieldBySeasonAndYear(Season.YALA, 2023);

        assertNotNull(result);
        assertEquals(90.0, result);
        verify(cropRepository, times(1)).findAverageYieldBySeasonAndYear(Season.YALA, 2023);
    }
}