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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FarmerServiceImplTest {

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
        farmer.setFarmerName("Thilak");

        farmerDTO = new FarmerDTO();
        farmerDTO.setFarmerName("Thilak");
    }

    @Test
    void saveFarmer_ShouldSaveAndReturnFarmerDTO() {
        when(farmerMapper.convertFarmerDtoToEntity(farmerDTO)).thenReturn(farmer);
        when(farmerRepository.save(farmer)).thenReturn(farmer);
        when(farmerMapper.convertFarmerEntityToDto(farmer)).thenReturn(farmerDTO);

        FarmerDTO savedFarmerDTO = farmerService.saveFarmer(farmerDTO);

        assertThat(savedFarmerDTO).isEqualTo(farmerDTO);
        verify(farmerRepository).save(farmer);
        verify(farmerMapper).convertFarmerDtoToEntity(farmerDTO);
        verify(farmerMapper).convertFarmerEntityToDto(farmer);
    }

    @Test
    void updateFarmerById_ShouldUpdateAndReturnFarmerDTO_WhenFarmerExists() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.of(farmer));
        when(farmerMapper.convertFarmerDtoToEntity(farmerDTO)).thenReturn(farmer);
        when(farmerRepository.save(farmer)).thenReturn(farmer);
        when(farmerMapper.convertFarmerEntityToDto(farmer)).thenReturn(farmerDTO);

        FarmerDTO updatedFarmerDTO = farmerService.updateFarmerById(1L, farmerDTO);

        assertThat(updatedFarmerDTO).isEqualTo(farmerDTO);
        verify(farmerRepository).findById(1L);
        verify(farmerMapper).convertFarmerDtoToEntity(farmerDTO);
        verify(farmerRepository).save(farmer);
        verify(farmerMapper).convertFarmerEntityToDto(farmer);
    }

    @Test
    void updateFarmerById_ShouldThrowException_WhenFarmerDoesNotExist() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> farmerService.updateFarmerById(1L, farmerDTO))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("No farmer with id 1 found.");

        verify(farmerRepository).findById(1L);
    }

    @Test
    void getAllFarmers_ShouldReturnListOfFarmerDTOs() {
        when(farmerRepository.findAll()).thenReturn(List.of(farmer));
        when(farmerMapper.convertFarmerEntityToDto(farmer)).thenReturn(farmerDTO);

        List<FarmerDTO> farmerDTOs = farmerService.getAllFarmers();

        assertThat(farmerDTOs).containsExactly(farmerDTO);
        verify(farmerRepository).findAll();
        verify(farmerMapper).convertFarmerEntityToDto(farmer);
    }

    @Test
    void getFarmerById_ShouldReturnFarmerDTO_WhenFarmerExists() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.of(farmer));
        when(farmerMapper.convertFarmerEntityToDto(farmer)).thenReturn(farmerDTO);

        FarmerDTO foundFarmerDTO = farmerService.getFarmerById(1L);

        assertThat(foundFarmerDTO).isEqualTo(farmerDTO);
        verify(farmerRepository).findById(1L);
        verify(farmerMapper).convertFarmerEntityToDto(farmer);
    }

    @Test
    void getFarmerById_ShouldThrowException_WhenFarmerDoesNotExist() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> farmerService.getFarmerById(1L))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("Farmer was not found with id 1");

        verify(farmerRepository).findById(1L);
    }

    @Test
    void deleteFarmer_ShouldDeleteFarmer_WhenFarmerExists() {
        when(farmerRepository.existsById(1L)).thenReturn(true);

        farmerService.deleteFarmer(1L);

        verify(farmerRepository).existsById(1L);
        verify(farmerRepository).deleteById(1L);
    }

    @Test
    void deleteFarmer_ShouldThrowException_WhenFarmerDoesNotExist() {
        when(farmerRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> farmerService.deleteFarmer(1L))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("No farmer with id 1 found.");

        verify(farmerRepository).existsById(1L);
    }
}
