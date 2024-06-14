package com.example.farmcollector.service;

import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.service.farmer.FarmerServiceImpl;
import com.example.farmcollector.util.FarmerMapper;
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
public class FarmerServiceImplTest {

    @Mock
    private FarmerRepository farmerRepository;

    @Mock
    private FarmerMapper farmerMapper;

    @InjectMocks
    private FarmerServiceImpl farmerService;

    private Farmer farmer;
    private FarmerDTO farmerDTO;
    private final String farmerId = "F-0001";

    @BeforeEach
    void setUp() {
        Farm farm = new Farm();
        String farmId = "L-0001";
        farm.setFarmId(farmId);
        farm.setFarmName("Test Farm");
        farm.setLocation("Test Location");
        farm.setFarmArea(100.0);

        farmer = new Farmer();
        farmer.setFarmerId(farmerId);
        farmer.setFarmerName("Darshana");
        farmer.setFarm(farm);

        farmerDTO = new FarmerDTO();
        farmerDTO.setFarmerName("Darshana");
        farmerDTO.setFarm(farm);
    }

    @Test
    void saveFarmer_success() {
        when(farmerMapper.convertFarmerDtoToEntity(any(FarmerDTO.class))).thenReturn(farmer);
        when(farmerRepository.save(any(Farmer.class))).thenReturn(farmer);
        when(farmerMapper.convertFarmerEntityToDto(any(Farmer.class))).thenReturn(farmerDTO);

        FarmerDTO result = farmerService.saveFarmer(farmerDTO);

        assertNotNull(result);
        assertEquals(farmerDTO.getFarmerName(), result.getFarmerName());

        verify(farmerRepository, times(1)).save(any(Farmer.class));
    }

    @Test
    void updateFarmerById_success() {
        when(farmerRepository.existsFarmerByFarmerId(farmerId)).thenReturn(true);
        when(farmerMapper.convertFarmerDtoToEntity(any(FarmerDTO.class))).thenReturn(farmer);
        when(farmerRepository.save(any(Farmer.class))).thenReturn(farmer);
        when(farmerMapper.convertFarmerEntityToDto(any(Farmer.class))).thenReturn(farmerDTO);

        FarmerDTO result = farmerService.updateFarmerById(farmerId, farmerDTO);

        assertNotNull(result);
        assertEquals(farmerDTO.getFarmerName(), result.getFarmerName());

        verify(farmerRepository, times(1)).existsFarmerByFarmerId(farmerId);
        verify(farmerMapper, times(1)).convertFarmerDtoToEntity(farmerDTO);
        verify(farmerRepository, times(1)).save(any(Farmer.class));
        verify(farmerMapper, times(1)).convertFarmerEntityToDto(farmer);
    }

    @Test
    void updateFarmerById_notFound() {
        when(farmerRepository.existsFarmerByFarmerId(farmerId)).thenReturn(false);

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmerService.updateFarmerById(farmerId, farmerDTO);
        });

        assertEquals("No farmer with id F-0001 found.", exception.getMessage());

        verify(farmerRepository, times(1)).existsFarmerByFarmerId(farmerId);
        verify(farmerRepository, times(0)).save(any(Farmer.class));
        verify(farmerMapper, never()).convertFarmerDtoToEntity(farmerDTO);
        verify(farmerMapper, never()).convertFarmerEntityToDto(farmer);
    }

    @Test
    void getAllFarmers_success() {
        when(farmerRepository.findAll()).thenReturn(List.of(farmer));
        when(farmerMapper.convertFarmerEntityToDto(any(Farmer.class))).thenReturn(farmerDTO);

        List<FarmerDTO> result = farmerService.getAllFarmers();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(farmerRepository, times(1)).findAll();
    }

    @Test
    void getFarmerById_success() {
        when(farmerRepository.findFarmerByFarmerId(farmerId)).thenReturn(Optional.of(farmer));
        when(farmerMapper.convertFarmerEntityToDto(any(Farmer.class))).thenReturn(farmerDTO);

        FarmerDTO result = farmerService.getFarmerById(farmerId);

        assertNotNull(result);
        assertEquals(farmerDTO.getFarmerName(), result.getFarmerName());

        verify(farmerRepository, times(1)).findFarmerByFarmerId(farmerId);
    }

    @Test
    void getFarmerById_notFound() {
        when(farmerRepository.findFarmerByFarmerId(farmerId)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmerService.getFarmerById(farmerId);
        });

        assertEquals("Farmer was not found with id F-0001", exception.getMessage());

        verify(farmerRepository, times(1)).findFarmerByFarmerId(farmerId);
    }

    @Test
    void deleteFarmer_ById_success() {
        when(farmerRepository.existsFarmerByFarmerId(farmerId)).thenReturn(true);

        farmerService.deleteFarmerById(farmerId);

        verify(farmerRepository, times(1)).existsFarmerByFarmerId(farmerId);
        verify(farmerRepository, times(1)).deleteFarmerByFarmerId(farmerId);
    }

    @Test
    void deleteFarmer_ById_notFound() {
        when(farmerRepository.existsFarmerByFarmerId(farmerId)).thenReturn(false);

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmerService.deleteFarmerById(farmerId);
        });

        assertEquals("No farmer with id F-0001 found.", exception.getMessage());

        verify(farmerRepository, times(1)).existsFarmerByFarmerId(farmerId);
        verify(farmerRepository, times(0)).deleteFarmerByFarmerId(farmerId);
    }
}