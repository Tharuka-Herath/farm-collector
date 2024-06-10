package com.example.farmcollector.service;

import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.service.farm.FarmServiceImpl;
import com.example.farmcollector.util.FarmMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FarmServiceImplTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmMapper farmMapper;

    @InjectMocks
    private FarmServiceImpl farmService;

    private Farm farm;
    private FarmDTO farmDTO;

    @BeforeEach
    void setUp() {
        farm = new Farm();
        farm.setId(1L);
        farm.setFarmName("Farm A");
        farm.setLocation("Matara");
        farm.setFarmArea(100.0);

        farmDTO = new FarmDTO();
        farmDTO.setFarmName("Farm A");
        farmDTO.setLocation("Matara");
        farmDTO.setFarmArea(100.0);
    }

    @Test
    void saveFarm_ShouldSaveAndReturnFarmDTO() {
        when(farmMapper.convertFarmDtoToEntity(farmDTO)).thenReturn(farm);
        when(farmRepository.save(farm)).thenReturn(farm);
        when(farmMapper.convertFarmEntityToDto(farm)).thenReturn(farmDTO);

        FarmDTO savedFarmDTO = farmService.saveFarm(farmDTO);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(savedFarmDTO).isEqualTo(farmDTO);
        softly.assertThat(savedFarmDTO.getFarmName()).isEqualToIgnoringCase("Farm A");
        softly.assertThat(savedFarmDTO.getLocation()).isEqualToIgnoringCase("Matara");
        softly.assertThat(savedFarmDTO.getFarmArea()).isEqualTo(100.0);
        softly.assertAll();

        verify(farmRepository).save(farm);
        verify(farmMapper).convertFarmDtoToEntity(farmDTO);
        verify(farmMapper).convertFarmEntityToDto(farm);
    }

    @Test
    void updateFarm_ShouldUpdateAndReturnFarmDTO_WhenFarmExists() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmMapper.convertFarmDtoToEntity(farmDTO)).thenReturn(farm);
        when(farmRepository.save(farm)).thenReturn(farm);
        when(farmMapper.convertFarmEntityToDto(farm)).thenReturn(farmDTO);

        Optional<FarmDTO> updatedFarmDTO = farmService.updateFarm(1L, farmDTO);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(updatedFarmDTO).isPresent();
        softly.assertThat(updatedFarmDTO.get()).isEqualTo(farmDTO);
        softly.assertThat(updatedFarmDTO.get().getFarmName()).isEqualTo("Farm A");
        softly.assertThat(updatedFarmDTO.get().getLocation()).isEqualTo("Matara");
        softly.assertThat(updatedFarmDTO.get().getFarmArea()).isEqualTo(100.0);
        softly.assertAll();

        verify(farmRepository).findById(1L);
        verify(farmMapper).convertFarmDtoToEntity(farmDTO);
        verify(farmRepository).save(farm);
        verify(farmMapper).convertFarmEntityToDto(farm);
    }

    @Test
    void updateFarm_ShouldReturnEmptyOptional_WhenFarmDoesNotExist() {
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<FarmDTO> updatedFarmDTO = farmService.updateFarm(1L, farmDTO);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(updatedFarmDTO).isEmpty();
        softly.assertAll();

        verify(farmRepository).findById(1L);
    }

    @Test
    void getAllFarms_ShouldReturnListOfFarmDTOs() {
        when(farmRepository.findAll()).thenReturn(List.of(farm));
        when(farmMapper.convertFarmEntityToDto(farm)).thenReturn(farmDTO);

        List<FarmDTO> farmDTOs = farmService.getAllFarms();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(farmDTOs).containsExactly(farmDTO);
        softly.assertAll();

        verify(farmRepository).findAll();
        verify(farmMapper).convertFarmEntityToDto(farm);
    }

    @Test
    void getFarmById_ShouldReturnFarmDTO_WhenFarmExists() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(farmMapper.convertFarmEntityToDto(farm)).thenReturn(farmDTO);

        FarmDTO foundFarmDTO = farmService.getFarmById(1L);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(foundFarmDTO).isEqualTo(farmDTO);
        softly.assertThat(foundFarmDTO.getFarmName()).isEqualTo("Farm A");
        softly.assertThat(foundFarmDTO.getLocation()).isEqualTo("Matara");
        softly.assertThat(foundFarmDTO.getFarmArea()).isEqualTo(100.0);
        softly.assertAll();

        verify(farmRepository).findById(1L);
        verify(farmMapper).convertFarmEntityToDto(farm);
    }

    @Test
    void getFarmById_ShouldThrowException_WhenFarmDoesNotExist() {
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> farmService.getFarmById(1L))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("Farm not found with id: 1");

        verify(farmRepository).findById(1L);
    }

    @Test
    void deleteFarm_ShouldDeleteFarm_WhenFarmExists() {
        when(farmRepository.existsById(1L)).thenReturn(true);

        farmService.deleteFarm(1L);

        verify(farmRepository).existsById(1L);
        verify(farmRepository).deleteById(1L);
    }

    @Test
    void deleteFarm_ShouldThrowException_WhenFarmDoesNotExist() {
        when(farmRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> farmService.deleteFarm(1L))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("No farm with id 1 found.");

        verify(farmRepository).existsById(1L);
    }
}
