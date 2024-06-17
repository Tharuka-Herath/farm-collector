package com.example.farmcollector.service;

import com.example.farmcollector.dto.FarmDTO;
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

import java.util.ArrayList;
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
    private final String farmId = "L-0001";
    private final String farmerId = "F-0001";

    @BeforeEach
    void setUp() {
        farm = new Farm();
        farm.setFarmId(farmId);
        farm.setFarmName("Farm A");
        farm.setLocation("Galle");
        farm.setFarmArea(100.0);

        farmer = new Farmer();
        farmer.setFarmerId(farmerId);
        farmer.setFarmerName("Thilak");
        farmer.setFarm(farm);

        List<Farmer> farmersList = new ArrayList<>();
        farmersList.add(farmer);

        farmDTO = new FarmDTO();
        farmDTO.setFarmName("Farm A");
        farmDTO.setLocation("Galle");
        farmDTO.setFarmArea(100.0);
        farmDTO.setFarmers(farmersList);
    }

    @Test
    void saveFarm_AndFarmer_success() {
        when(farmMapper.convertFarmDtoToEntity(any(FarmDTO.class))).thenReturn(farm);
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.convertFarmEntityToDto(any(Farm.class))).thenReturn(farmDTO);

        FarmDTO result = farmService.saveFarm(farmDTO);

        assertNotNull(result);
        assertEquals(farmDTO.getFarmName(), result.getFarmName());
        assertEquals(farmDTO.getFarmers(), result.getFarmers());
        verify(farmRepository, times(1)).save(any(Farm.class));
    }

    @Test
    void updateFarm_success() {
        when(farmRepository.findFarmByFarmId(farmId)).thenReturn(Optional.of(farm));
        when(farmMapper.convertFarmDtoToEntity(any(FarmDTO.class))).thenReturn(farm);
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.convertFarmEntityToDto(any(Farm.class))).thenReturn(farmDTO);

        FarmDTO result = farmService.updateFarm(farmId, farmDTO);

        assertNotNull(result);
        assertEquals(farmDTO.getFarmName(), result.getFarmName());

        verify(farmRepository, times(1)).findFarmByFarmId(farmId);
        verify(farmRepository, times(1)).save(any(Farm.class));
    }

    @Test
    void updateFarm_notFound() {
        when(farmRepository.findFarmByFarmId(farmId)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> farmService.updateFarm(farmId, farmDTO));

        assertEquals("No record with " + farmId + "to update", exception.getMessage());
        verify(farmRepository, times(1)).findFarmByFarmId(farmId);
        verify(farmRepository, never()).save(any(Farm.class));
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
        when(farmRepository.findFarmByFarmId(farmId)).thenReturn(Optional.of(farm));
        when(farmMapper.convertFarmEntityToDto(any(Farm.class))).thenReturn(farmDTO);

        FarmDTO result = farmService.getFarmById(farmId);

        assertNotNull(result);
        assertEquals(farmDTO.getFarmName(), result.getFarmName());

        verify(farmRepository, times(1)).findFarmByFarmId(farmId);
    }

    @Test
    void getFarmById_notFound() {
        when(farmRepository.findFarmByFarmId(farmId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(FarmDataNotFoundException.class, () -> farmService.getFarmById(farmId));

        assertEquals("Farm not found with id: " + farmId, exception.getMessage());

        verify(farmRepository, times(1)).findFarmByFarmId(farmId);
    }

    @Test
    void deleteFarm_ById_success() {
        when(farmRepository.existsFarmByFarmId(farmId)).thenReturn(true);

        farmService.deleteFarmById(farmId);

        verify(farmRepository, times(1)).existsFarmByFarmId(farmId);
        verify(farmRepository, times(1)).deleteFarmByFarmId(farmId);
    }

    @Test
    void deleteFarm_ById_notFound() {
        when(farmRepository.existsFarmByFarmId(farmId)).thenReturn(false);

        Exception exception = assertThrows(FarmDataNotFoundException.class, () -> farmService.deleteFarmById(farmId));

        assertEquals("No farm with id " + farmId + "found", exception.getMessage());

        verify(farmRepository, times(1)).existsFarmByFarmId(farmId);
        verify(farmRepository, times(0)).deleteFarmByFarmId(farmId);
    }

    @Test
    void addFarmerToFarm_success() {
        when(farmRepository.findFarmByFarmId(farmId)).thenReturn(Optional.of(farm));
        when(farmerRepository.findFarmerByFarmerId(farmerId)).thenReturn(Optional.of(farmer));
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        when(farmMapper.convertFarmEntityToDto(any(Farm.class))).thenReturn(farmDTO);

        FarmDTO result = farmService.addFarmerToFarm(farmId, farmerId);

        assertNotNull(result);
        assertEquals(farmDTO.getFarmName(), result.getFarmName());
        assertEquals(1, farm.getFarmers().size());
        assertTrue(farm.getFarmers().contains(farmer));
        assertEquals(farm, farmer.getFarm());

        verify(farmRepository, times(1)).findFarmByFarmId(farmId);
        verify(farmerRepository, times(1)).findFarmerByFarmerId(farmerId);
        verify(farmRepository, times(1)).save(farm);
        verify(farmMapper, times(1)).convertFarmEntityToDto(farm);
    }

    @Test
    void addFarmerToFarm_farmNotFound() {
        when(farmRepository.findFarmByFarmId(farmId)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> farmService.addFarmerToFarm(farmId, farmerId));

        assertEquals("No farm with id " + farmId + "found", exception.getMessage());

        verify(farmRepository, times(1)).findFarmByFarmId(farmId);
        verify(farmerRepository, times(0)).findFarmerByFarmerId(farmerId);
        verify(farmRepository, times(0)).save(any(Farm.class));
        verify(farmMapper, times(0)).convertFarmEntityToDto(any(Farm.class));
    }

    @Test
    void addFarmerToFarm_farmerNotFound() {
        when(farmRepository.findFarmByFarmId(farmId)).thenReturn(Optional.of(farm));
        when(farmerRepository.findFarmerByFarmerId(farmerId)).thenReturn(Optional.empty());

        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> farmService.addFarmerToFarm(farmId, farmerId));

        assertEquals("No farmer with id " + farmerId + "found", exception.getMessage());

        verify(farmRepository, times(1)).findFarmByFarmId(farmId);
        verify(farmerRepository, times(1)).findFarmerByFarmerId(farmerId);
        verify(farmRepository, times(0)).save(any(Farm.class));
        verify(farmMapper, times(0)).convertFarmEntityToDto(any(Farm.class));
    }
}
