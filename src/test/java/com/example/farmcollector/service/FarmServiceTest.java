package com.example.farmcollector.service;

import com.example.farmcollector.dto.FarmDTO;
import com.example.farmcollector.enums.Season;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Crop;
import com.example.farmcollector.model.Farm;
import com.example.farmcollector.repository.CropRepository;
import com.example.farmcollector.repository.FarmRepository;
import com.example.farmcollector.service.farm.FarmServiceImpl;
import com.example.farmcollector.util.CropMapper;
import com.example.farmcollector.util.FarmMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class FarmServiceTest {
    @Mock
    private FarmRepository farmRepository;

    @Mock
    FarmMapper farmMapper;

    @Mock
    private CropRepository cropRepository;

    @Mock
    CropMapper cropMapper;

    @InjectMocks
    private FarmServiceImpl farmService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    void shouldSaveFarm() {
        //Arrange

        FarmDTO farmDTO = new FarmDTO("L-0001", "Farm A", "Galle", 80.0, null, null);
        Farm farmEntity = new Farm(1L, "L-0001", "Farm A", "Galle", 80.0, null, null, null, null);
        Farm savedFarmEntity = new Farm(1L, "L-0001", "Farm A", "Galle", 80.0, null, null, null, null);
        FarmDTO savedFarmDto = new FarmDTO("L-0001", "Farm A", "Galle", 80.0, null, null);

        when(farmMapper.convertFarmDtoToEntity(farmDTO)).thenReturn(farmEntity);
        when(farmRepository.save(farmEntity)).thenReturn(savedFarmEntity);
        when(farmMapper.convertFarmEntityToDto(savedFarmEntity)).thenReturn(savedFarmDto);

        //Act
        FarmDTO result = farmService.saveFarm(farmDTO);

        //Assert
        assertNotNull(result);
        assertEquals(savedFarmDto, result);
        assertEquals(savedFarmDto.getFarmId(), result.getFarmId());
        assertEquals(savedFarmDto.getFarmName(), result.getFarmName());
        assertEquals(savedFarmDto.getLocation(), result.getLocation());
        assertEquals(savedFarmDto.getFarmArea(), result.getFarmArea());

        //verify
        verify(farmMapper, times(1)).convertFarmDtoToEntity(farmDTO);
        verify(farmRepository, times(1)).save(farmEntity);
        verify(farmMapper, times(1)).convertFarmEntityToDto(savedFarmEntity);
    }

    @Test
    void shouldUpdateFarmWhenPresent() {
        //Arrange
        Long id = 1L;
        FarmDTO newFarmDetailsDto = new FarmDTO("L-0001", "Farm B", "Matara", 150.0, null, null);
        Optional<Farm> oldFarmDetailsOptional = Optional.of(new Farm(id, "L-0001", "Farm A", "Galle", 80.0, null, null, null, null));
        Farm farmToUpdateEntity = new Farm(id, "L-0001", "Farm A", "Galle", 80.0, null, null, null, null);
        Farm updatedFarmEntity = new Farm(id, "L-0001", "Farm B", "Matara", 150.0, null, null, null, null);
        FarmDTO updatedFarmDto = new FarmDTO("L-0001", "Farm B", "Matara", 150.0, null, null);

        when(farmRepository.findById(id)).thenReturn(oldFarmDetailsOptional);
        when(farmMapper.convertFarmDtoToEntity(newFarmDetailsDto)).thenReturn(farmToUpdateEntity);
        when(farmRepository.save(farmToUpdateEntity)).thenReturn(updatedFarmEntity);
        when(farmMapper.convertFarmEntityToDto(updatedFarmEntity)).thenReturn(updatedFarmDto);

        //Act
        FarmDTO result = farmService.updateFarm(id, newFarmDetailsDto);

        //Assert
        assertNotNull(result);
        assertEquals(updatedFarmDto, result);
        assertEquals(updatedFarmDto.getFarmId(), result.getFarmId());
        assertEquals(updatedFarmDto.getFarmName(), result.getFarmName());
        assertEquals(updatedFarmDto.getLocation(), result.getLocation());
        assertEquals(updatedFarmDto.getFarmArea(), result.getFarmArea());

        //Verify
        verify(farmRepository, times(1)).findById(id);
        verify(farmMapper, times(1)).convertFarmDtoToEntity(newFarmDetailsDto);
        verify(farmRepository, times(1)).save(farmToUpdateEntity);
        verify(farmMapper, times(1)).convertFarmEntityToDto(updatedFarmEntity);
    }

    @Test
    void shouldReturnEmptyWhenFarmNotFound() {
        //Arrange
        Long id = 1L;
        FarmDTO farmDTO = new FarmDTO("L-0001", "Farm B", "Matara", 150.0, null, null);

        when(farmRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, ()->farmService.updateFarm(id, farmDTO));

        assertEquals("No farm with id " + id + " found.", exception.getMessage());

        //verify
        verify(farmRepository, times(1)).findById(id);
        verify(farmMapper, never()).convertFarmDtoToEntity(any());
        verify(farmRepository, never()).save(any());
        verify(farmMapper, never()).convertFarmEntityToDto(any());
    }

    @Test
    void shouldGetAllFarms() {
        // Arrange
        List<Farm> farms = List.of(new Farm(1L, "L-0001", "Farm A", "Galle", 80.0, null, null, null, null), new Farm(2L, "L-0002", "Farm B", "Matara", 150.0, null, null, null, null));
        List<FarmDTO> farmDTOs = List.of(new FarmDTO("L-0001", "Farm A", "Galle", 80.0, null, null), new FarmDTO("L-0002", "Farm B", "Matara", 150.0, null, null));

        when(farmRepository.findAll()).thenReturn(farms);
        when(farmMapper.convertFarmEntityToDto(farms.get(0))).thenReturn(farmDTOs.get(0));
        when(farmMapper.convertFarmEntityToDto(farms.get(1))).thenReturn(farmDTOs.get(1));

        // Act
        List<FarmDTO> result = farmService.getAllFarms();

        // Assert
        assertEquals(farmDTOs.size(), result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(1));
        assertEquals(farmDTOs.get(0), result.get(0));
        assertEquals(farmDTOs.get(1), result.get(1));

        //Verify
        verify(farmRepository, times(1)).findAll();
        verify(farmMapper, times(1)).convertFarmEntityToDto(farms.get(0));
        verify(farmMapper, times(1)).convertFarmEntityToDto(farms.get(1));
    }


    @Test
    void shouldReturnFarmDtoWhenFarmIsFound() {
        // Arrange
        Long id = 1L;
        Farm farm = new Farm(id, "L-0001", "Farm A", "Galle", 80.0, null, null, null, null);
        FarmDTO farmDTO = new FarmDTO("L-0001", "Farm A", "Galle", 80.0, null, null);

        when(farmRepository.findById(id)).thenReturn(Optional.of(farm));
        when(farmMapper.convertFarmEntityToDto(farm)).thenReturn(farmDTO);

        // Act
        FarmDTO result = farmService.getFarmById(id);

        // Assert
        assertEquals(farmDTO, result);
        assertEquals(farmDTO.getFarmId(), result.getFarmId());
        assertEquals(farmDTO.getFarmName(), result.getFarmName());
        assertEquals(farmDTO.getLocation(), result.getLocation());
        assertEquals(farmDTO.getFarmArea(), result.getFarmArea());

        //Verify
        verify(farmRepository, times(1)).findById(id);
        verify(farmMapper, times(1)).convertFarmEntityToDto(farm);
    }

    @Test
    void shouldThrowExceptionWhenFarmIsNotFound() {
        // Arrange
        Long id = 1L;
        when(farmRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmService.getFarmById(id);
        });

        assertEquals("Farm not found with id: " + id, exception.getMessage());
        verify(farmRepository, times(1)).findById(id);
        verify(farmMapper, never()).convertFarmEntityToDto(any());
    }


    @Test
    void shouldDeleteFarmWhenExists() {
        // Arrange
        Long id = 1L;
        when(farmRepository.existsById(id)).thenReturn(true);

        // Act
        farmService.deleteFarm(id);

        // Assert
        assertFalse(farmRepository.findById(id).isPresent());

        verify(farmRepository, times(1)).existsById(id);
        verify(farmRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenNoFarmToDelete() {
        //Arrange
        Long id = 1L;

        when(farmRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmService.deleteFarm(id);
        });

        assertEquals("No farm with id " + id + " found.", exception.getMessage());

        verify(farmRepository, times(1)).existsById(any());
        verify(farmRepository, never()).deleteById(any());
    }

    @Test
    void shouldThrowExceptionWhenCropNotFound() {
        //Arrange
        Long farmId = 1L;
        Long cropId = 1L;

        when(cropRepository.findById(cropId)).thenReturn(Optional.empty());

        //Act & Assert
        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmService.addCropToFarm(farmId, cropId);
        });

        assertEquals("Crop not found with id: " + cropId, exception.getMessage());
        verify(cropRepository, times(1)).findById(cropId);
        verify(farmRepository, never()).findById(farmId);
        verify(farmRepository, never()).save(any());
        verify(farmMapper, never()).convertFarmEntityToDto(any());
    }

    @Test
    void shouldThrowExceptionWhenFarmNotFound() {
        //Arrange
        Long farmId = 1L;
        Long cropId = 1L;
        Crop crop = new Crop(cropId, "C-0001", "Wheat", Season.YALA, 2024, 150.0, 120.0, null, null, null, null);

        when(cropRepository.findById(cropId)).thenReturn(Optional.of(crop));
        when(farmRepository.findById(farmId)).thenReturn(Optional.empty());

        //Act & Assert
        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> {
            farmService.addCropToFarm(farmId, cropId);
        });
        assertEquals("Farm not found with id: " + farmId, exception.getMessage());
        verify(cropRepository, times(1)).findById(cropId);
        verify(farmRepository, times(1)).findById(farmId);
        verify(farmRepository, never()).save(any());
        verify(farmMapper, never()).convertFarmEntityToDto(any());
    }

    @Test
    void shouldAddCropToFarmWhenBothFarmAndCropAreFound() {
        //Arrange
        Long farmId = 1L;
        Long cropId = 1L;

        Crop crop = new Crop(cropId, "C-0001", "Wheat", Season.YALA, 2024, 150.0, 120.0, null, null, null, null);
        Farm farm = new Farm(farmId, "L-0001", "Farm A", "Galle", 80.0, null, null, null, new HashSet<>());
        Farm updatedFarm = new Farm(farmId, "L-0001", "Farm A", "Galle", 80.0, null, null, null, Set.of(crop));
        FarmDTO updatedFarmDto = new FarmDTO("L-0001", "Farm A", "Galle", 80.0, null, Set.of(crop));

        when(cropRepository.findById(cropId)).thenReturn(Optional.of(crop));
        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(farmRepository.save(farm)).thenReturn(updatedFarm);
        when(farmMapper.convertFarmEntityToDto(updatedFarm)).thenReturn(updatedFarmDto);

        //Act
        FarmDTO result = farmService.addCropToFarm(farmId, cropId);

        //Assert
        assertEquals(updatedFarmDto, result);
        assertEquals(updatedFarmDto.getCrops(), result.getCrops());

        verify(cropRepository, times(1)).findById(cropId);
        verify(farmRepository, times(1)).findById(farmId);
        verify(farmRepository, times(1)).save(farm);
        verify(farmMapper, times(1)).convertFarmEntityToDto(updatedFarm);
    }
}
