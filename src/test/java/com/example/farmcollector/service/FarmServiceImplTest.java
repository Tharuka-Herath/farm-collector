package com.example.farmcollector.service;

import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.service.farm.FarmServiceImpl;
import com.example.farmcollector.util.FarmMapper;
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
public class FarmServiceImplTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmMapper farmMapper;
    
    @Mock
    private FarmerRepository farmerRepository;

    @InjectMocks
    private FarmServiceImpl farmService;

    private Farm farm;
    private FarmDTO farmDTO;
    private Farmer farmer;
    private FarmerDTO farmerDTO;

    @BeforeEach
    void setUp() {
        farm = new Farm();
        farm.setId(1L);
        farm.setFarmName("Test Farm");
        farm.setLocation("Test Location");
        farm.setFarmArea(100.0);

        farmDTO = new FarmDTO();
        farmDTO.setFarmName("Test Farm");
        farmDTO.setLocation("Test Location");
        farmDTO.setFarmArea(100.0);

        farmer = new Farmer();
        farmer.setId(1L);
        farmer.setFarmerName("Thilak");
        farmer.setFarm(farm);

        farmerDTO = new FarmerDTO();
        farmerDTO.setFarmerName("Thilak");
        farmerDTO.setFarm(farm);
    }

    @Test
    void saveFarm_success() {
        when(farmMapper.convertFarmDtoToEntity(any(FarmDTO.class))).thenReturn(farm);
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.convertFarmEntityToDto(any(Farm.class))).thenReturn(farmDTO);

        FarmDTO result = farmService.saveFarm(farmDTO);

        assertNotNull(result);
        assertEquals(farmDTO.getFarmName(), result.getFarmName());
        verify(farmRepository, times(1)).save(any(Farm.class));
    }

    @Test
    void updateFarm_success() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmMapper.convertFarmDtoToEntity(any(FarmDTO.class))).thenReturn(farm);
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.convertFarmEntityToDto(any(Farm.class))).thenReturn(farmDTO);

        Optional<FarmDTO> result = farmService.updateFarm(1L, farmDTO);

        assertTrue(result.isPresent());
        assertEquals(farmDTO.getFarmName(), result.get().getFarmName());

        verify(farmRepository, times(1)).findById(1L);
        verify(farmRepository, times(1)).save(any(Farm.class));
    }

    @Test
    void updateFarm_notFound() {
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<FarmDTO> result = farmService.updateFarm(1L, farmDTO);

        assertFalse(result.isPresent());
        verify(farmRepository, times(1)).findById(1L);
        verify(farmRepository, times(0)).save(any(Farm.class));
    }

    @Test
    void getAllFarms_success() {
        when(farmRepository.findAll()).thenReturn(List.of(farm));
        when(farmMapper.convertFarmEntityToDto(any(Farm.class))).thenReturn(farmDTO);

        List<FarmDTO> result = farmService.getAllFarms();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(farmRepository, times(1)).findAll();
    }

    @Test
    void getFarmById_success() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmMapper.convertFarmEntityToDto(any(Farm.class))).thenReturn(farmDTO);

        FarmDTO result = farmService.getFarmById(1L);

        assertNotNull(result);
        assertEquals(farmDTO.getFarmName(), result.getFarmName());

        verify(farmRepository, times(1)).findById(1L);
    }

    @Test
    void getFarmById_notFound() {
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmService.getFarmById(1L);
        });

        assertEquals("Farm not found with id: 1", exception.getMessage());

        verify(farmRepository, times(1)).findById(1L);
    }

    @Test
    void deleteFarm_success() {
        when(farmRepository.existsById(1L)).thenReturn(true);

        farmService.deleteFarm(1L);

        verify(farmRepository, times(1)).existsById(1L);
        verify(farmRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteFarm_notFound() {
        when(farmRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmService.deleteFarm(1L);
        });

        assertEquals("No farm with id 1 found", exception.getMessage());

        verify(farmRepository, times(1)).existsById(1L);
        verify(farmRepository, times(0)).deleteById(1L);
    }

    @Test
    void addFarmerToFarm_success() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmerRepository.findById(1L)).thenReturn(Optional.of(farmer));
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.convertFarmEntityToDto(any(Farm.class))).thenReturn(farmDTO);

        FarmDTO result = farmService.addFarmerToFarm(1L, 1L);

        assertNotNull(result);
        assertEquals(farmDTO.getFarmName(), result.getFarmName());
        assertEquals(1, farm.getFarmers().size());
        assertTrue(farm.getFarmers().contains(farmer));
        assertEquals(farm, farmer.getFarm());

        verify(farmRepository, times(1)).findById(1L);
        verify(farmerRepository, times(1)).findById(1L);
        verify(farmRepository, times(1)).save(farm);
        verify(farmMapper, times(1)).convertFarmEntityToDto(farm);
    }

    @Test
    void addFarmerToFarm_farmNotFound() {
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmService.addFarmerToFarm(1L, 1L);
        });

        assertEquals("No farm with id 1 found", exception.getMessage());

        verify(farmRepository, times(1)).findById(1L);
        verify(farmerRepository, times(0)).findById(1L);
        verify(farmRepository, times(0)).save(any(Farm.class));
        verify(farmMapper, times(0)).convertFarmEntityToDto(any(Farm.class));
    }

    @Test
    void addFarmerToFarm_farmerNotFound() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmerRepository.findById(1L)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmService.addFarmerToFarm(1L, 1L);
        });

        assertEquals("No farmer with id 1 found", exception.getMessage());

        verify(farmRepository, times(1)).findById(1L);
        verify(farmerRepository, times(1)).findById(1L);
        verify(farmRepository, times(0)).save(any(Farm.class));
        verify(farmMapper, times(0)).convertFarmEntityToDto(any(Farm.class));
    }
}
