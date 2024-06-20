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
    private final String farmId="L-0001";
    private final String cropId="C-0001";
    private final String farmerId="F-0001";

    @BeforeEach
    void setUp() {
        farm = new Farm();
        farm.setFarmId(farmId);
        farm.setFarmName("Test Farm");

        farmer = new Farmer();
        farmer.setFarmerId(farmerId);
        farmer.setFarmerName("Darshana");

        crop = new Crop();
        crop.setCropId(cropId);
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
        when(cropRepository.findCropByCropId(cropId)).thenReturn(Optional.of(crop));
        when(cropMapper.convertCropEntityToDto(any(Crop.class))).thenReturn(cropDTO);

        CropDTO result = cropService.getCropById(cropId);

        assertNotNull(result);
        assertEquals(cropDTO.getCropType(), result.getCropType());

        verify(cropRepository, times(1)).findCropByCropId(cropId);
    }

    @Test
    void getCropById_notFound() {
        when(cropRepository.findCropByCropId(cropId)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            cropService.getCropById(cropId);
        });

        assertEquals("Crop not found for the given ID", exception.getMessage());

        verify(cropRepository, times(1)).findCropByCropId(cropId);
    }

    @Test
    void updateCropById_success() {
        when(cropRepository.findCropByCropId(cropId)).thenReturn(Optional.of(crop));
        when(cropMapper.convertCropDtoToEntity(any(CropDTO.class))).thenReturn(crop);
        when(cropRepository.save(any(Crop.class))).thenReturn(crop);
        when(cropMapper.convertCropEntityToDto(any(Crop.class))).thenReturn(cropDTO);

        CropDTO result = cropService.updateCropById(cropId, cropDTO);

        assertNotNull(result);
        assertEquals(cropDTO.getCropType(), result.getCropType());

        verify(cropRepository, times(1)).findCropByCropId(cropId);
        verify(cropRepository, times(1)).save(any(Crop.class));
    }

    @Test
    void updateCropById_notFound() {
        when(cropRepository.findCropByCropId(cropId)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            cropService.updateCropById("C-0001", cropDTO);
        });

        assertEquals("No crop record with " + cropId + " to update", exception.getMessage());

        verify(cropRepository, times(1)).findCropByCropId(cropId);
        verify(cropRepository, times(0)).save(any(Crop.class));
    }

    @Test
    void deleteCrop_success() {
        when(cropRepository.findCropByCropId(cropId)).thenReturn(Optional.of(crop));

        cropService.deleteCropByCropId(cropId);

        verify(cropRepository, times(1)).findCropByCropId(cropId);
        verify(cropRepository, times(1)).deleteCropByCropId(cropId);
    }

    @Test
    void deleteCrop_notFound() {
        when(cropRepository.findCropByCropId(cropId)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            cropService.deleteCropByCropId(cropId);
        });

        assertEquals("No crop record found with id: " + cropId, exception.getMessage());

        verify(cropRepository, times(1)).findCropByCropId(cropId);
        verify(cropRepository, times(0)).deleteCropByCropId(cropId);
    }




    @Test
    void findAllByCropType_success() {
        when(cropRepository.findAllByCropType("Wheat")).thenReturn(List.of(crop));
        when(cropMapper.convertCropEntityListToDtoList(List.of(crop))).thenReturn(List.of(cropDTO));

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


    @Test
    void findCropsWithFarmLocationByCropType_success() {
        List<Object[]> mockResult = List.of(
                new Object[]{"Crop1", "Location1"},
                new Object[]{"Crop2", "Location2"}
        );

        when(cropRepository.findCropsWithFarmLocationByCropType("Wheat")).thenReturn(mockResult);

        List<Object[]> result = cropService.findCropsWithFarmLocationByCropType("Wheat");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Crop1", result.get(0)[0]);
        assertEquals("Location1", result.get(0)[1]);

        verify(cropRepository, times(1)).findCropsWithFarmLocationByCropType("Wheat");
    }

    @Test
    void findCropsWithFarmLocationByCropType_emptyResult() {
        when(cropRepository.findCropsWithFarmLocationByCropType("Wheat")).thenReturn(List.of());

        List<Object[]> result = cropService.findCropsWithFarmLocationByCropType("Wheat");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(cropRepository, times(1)).findCropsWithFarmLocationByCropType("Wheat");
    }

    @Test
    void findCropsByFarmNameAndSeason_success() {
        List<Object[]> mockResult = List.of(
                new Object[]{"Crop1", "YALA"},
                new Object[]{"Crop2", "YALA"}
        );

        when(cropRepository.findCropsByFarmNameAndSeason("Test Farm", Season.YALA)).thenReturn(mockResult);

        List<Object[]> result = cropService.findCropsByFarmNameAndSeason("Test Farm", Season.YALA);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Crop1", result.get(0)[0]);
        assertEquals("YALA", result.get(0)[1]);

        verify(cropRepository, times(1)).findCropsByFarmNameAndSeason("Test Farm", Season.YALA);
    }

    @Test
    void findCropsByFarmNameAndSeason_emptyResult() {
        when(cropRepository.findCropsByFarmNameAndSeason("Test Farm", Season.YALA)).thenReturn(List.of());

        List<Object[]> result = cropService.findCropsByFarmNameAndSeason("Test Farm", Season.YALA);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(cropRepository, times(1)).findCropsByFarmNameAndSeason("Test Farm", Season.YALA);
    }
}