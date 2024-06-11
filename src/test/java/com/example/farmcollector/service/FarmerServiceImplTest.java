package com.example.farmcollector.service;

import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
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

    @BeforeEach
    void setUp() {

        farmer = new Farmer();
        farmer.setId(1L);
        farmer.setFarmerName("John Doe");

        farmerDTO = new FarmerDTO();
        farmerDTO.setFarmerName("John Doe");
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
        when(farmerRepository.findById(1L)).thenReturn(Optional.of(farmer));
        when(farmerMapper.convertFarmerDtoToEntity(any(FarmerDTO.class))).thenReturn(farmer);
        when(farmerRepository.save(any(Farmer.class))).thenReturn(farmer);
        when(farmerMapper.convertFarmerEntityToDto(any(Farmer.class))).thenReturn(farmerDTO);

        FarmerDTO result = farmerService.updateFarmerById(1L, farmerDTO);

        assertNotNull(result);
        assertEquals(farmerDTO.getFarmerName(), result.getFarmerName());
        verify(farmerRepository, times(1)).findById(1L);
        verify(farmerRepository, times(1)).save(any(Farmer.class));
    }

    @Test
    void updateFarmerById_notFound() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmerService.updateFarmerById(1L, farmerDTO);
        });

        assertEquals("No farmer with id 1 found.", exception.getMessage());
        verify(farmerRepository, times(1)).findById(1L);
        verify(farmerRepository, times(0)).save(any(Farmer.class));
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
        when(farmerRepository.findById(1L)).thenReturn(Optional.of(farmer));
        when(farmerMapper.convertFarmerEntityToDto(any(Farmer.class))).thenReturn(farmerDTO);

        FarmerDTO result = farmerService.getFarmerById(1L);

        assertNotNull(result);
        assertEquals(farmerDTO.getFarmerName(), result.getFarmerName());
        verify(farmerRepository, times(1)).findById(1L);
    }

    @Test
    void getFarmerById_notFound() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmerService.getFarmerById(1L);
        });

        assertEquals("Farmer was not found with id 1", exception.getMessage());
        verify(farmerRepository, times(1)).findById(1L);
    }

    @Test
    void deleteFarmer_success() {
        when(farmerRepository.existsById(1L)).thenReturn(true);

        farmerService.deleteFarmer(1L);

        verify(farmerRepository, times(1)).existsById(1L);
        verify(farmerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteFarmer_notFound() {
        when(farmerRepository.existsById(1L)).thenReturn(false);

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmerService.deleteFarmer(1L);
        });

        assertEquals("No farmer with id 1 found.", exception.getMessage());
        verify(farmerRepository, times(1)).existsById(1L);
        verify(farmerRepository, times(0)).deleteById(1L);
    }
}