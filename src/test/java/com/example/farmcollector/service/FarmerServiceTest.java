package com.example.farmcollector.service;

import com.example.farmcollector.dto.FarmerDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Farmer;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.service.farmer.FarmerServiceImpl;
import com.example.farmcollector.util.FarmerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FarmerServiceTest {

    @Mock
    private FarmerRepository farmerRepository;

    @Mock
    private FarmerMapper farmerMapper;

    @InjectMocks
    private FarmerServiceImpl farmerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveFarmer() {
        //Arrange
        Long farmerId = 1L;
        FarmerDTO farmerDTO = new FarmerDTO("Thilak", null);
        Farmer farmer = new Farmer(farmerId, "F-0001", "Thilak", null, null, null, null);
        Farmer savedFarmer = new Farmer(farmerId, "F-0001", "Thilak", null, null, null, null);
        FarmerDTO savedFarmerDto = new FarmerDTO("Thilak", null);

        when(farmerMapper.convertFarmerDtoToEntity(farmerDTO)).thenReturn(farmer);
        when(farmerRepository.save(farmer)).thenReturn(savedFarmer);
        when(farmerMapper.convertFarmerEntityToDto(savedFarmer)).thenReturn(savedFarmerDto);

        //Act
        FarmerDTO result = farmerService.saveFarmer(farmerDTO);

        //Assert
        assertEquals(savedFarmerDto, result);
        assertEquals(savedFarmerDto.getFarmerName(), result.getFarmerName());

        //verify
        verify(farmerMapper, times(1)).convertFarmerDtoToEntity(farmerDTO);
        verify(farmerRepository, times(1)).save(farmer);
        verify(farmerMapper, times(1)).convertFarmerEntityToDto(savedFarmer);
    }

    @Test
    void shouldUpdateFarmerWhenExists() {
        //Arrange
        Long id = 1L;
        FarmerDTO newFarmerDetailsDto = new FarmerDTO("namal", null);
        Optional<Farmer> farmerToUpdate = Optional.of(new Farmer(id, "F-0001", "Mithila", null, null, null, null));
        Farmer newFarmerDetailsEntity = new Farmer(id, "F-0001", "namal", null, null, null, null);
        Farmer updatedFarmerEntity = new Farmer(id, "F-0001", "namal", null, null, null, null);
        FarmerDTO updatedFarmerDto = new FarmerDTO("namal", null);

        when(farmerRepository.findById(id)).thenReturn(farmerToUpdate);
        when(farmerMapper.convertFarmerDtoToEntity(newFarmerDetailsDto)).thenReturn(newFarmerDetailsEntity);
        when(farmerRepository.save(newFarmerDetailsEntity)).thenReturn(updatedFarmerEntity);
        when(farmerMapper.convertFarmerEntityToDto(updatedFarmerEntity)).thenReturn(updatedFarmerDto);

        //Act
        FarmerDTO result = farmerService.updateFarmerById(id, newFarmerDetailsDto);

        //Assert
        assertEquals(updatedFarmerDto, result);
        assertEquals(newFarmerDetailsDto.getFarmerName(), result.getFarmerName());

        //Verifyx
        verify(farmerRepository, times(1)).findById(id);
        verify(farmerMapper, times(1)).convertFarmerDtoToEntity(newFarmerDetailsDto);
        verify(farmerRepository, times(1)).save(newFarmerDetailsEntity);
        verify(farmerMapper, times(1)).convertFarmerEntityToDto(updatedFarmerEntity);
    }

    @Test
    void shouldThrowExceptionWhenFarmerNotFound() {
        //Arrange
        Long id = 1L;
        FarmerDTO newFarmerDetailsDto = new FarmerDTO("namal", null);

        when(farmerRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () ->
                farmerService.updateFarmerById(id, newFarmerDetailsDto));

        assertEquals("No farmer with id " + id + " found.", exception.getMessage());

        verify(farmerRepository, times(1)).findById(id);
        verify(farmerMapper, never()).convertFarmerDtoToEntity(any());
        verify(farmerRepository, never()).save(any());
        verify(farmerMapper, never()).convertFarmerEntityToDto(any());
    }

    @Test
    void shouldGetAllFarmers() {
        //Arrange
        Farmer farmer1 = new Farmer(1L, "F-0001", "Thilak", null, null, null, null);
        FarmerDTO farmerDTO1 = new FarmerDTO("Thilak", null);

        Farmer farmer2 = new Farmer(2L, "F-0002", "Silva", null, null, null, null);
        FarmerDTO farmerDTO2 = new FarmerDTO("Silva", null);

        List<Farmer> farmersList = Arrays.asList(farmer1, farmer2);
        List<FarmerDTO> farmerDTOList = Arrays.asList(farmerDTO1, farmerDTO2);


        when(farmerRepository.findAll()).thenReturn(farmersList);
        when(farmerMapper.convertFarmerEntityToDto(farmersList.get(0))).thenReturn(farmerDTOList.get(0));
        when(farmerMapper.convertFarmerEntityToDto(farmersList.get(1))).thenReturn(farmerDTOList.get(1));


        //Act
        List<FarmerDTO> result = farmerService.getAllFarmers();

        //Assert
        assertNotNull(result);
        assertEquals(farmerDTOList, result);
        assertEquals(farmerDTOList.size(), result.size());
        assertEquals(farmerDTOList.get(0), result.get(0));
        assertEquals(farmerDTOList.get(1), result.get(1));

        //Verify
        verify(farmerRepository, times(1)).findAll();
        verify(farmerMapper, times(2)).convertFarmerEntityToDto(any(Farmer.class));
    }

    @Test
    void shouldReturnFarmerByIDWhenExists() {
        //Arrange
        Long id = 1L;
        Optional<Farmer> getFarmer = Optional.of(new Farmer(1L, "F-0001", "Thilak", null, null, null, null));
        FarmerDTO farmerById = new FarmerDTO("Thilak", null);

        when(farmerRepository.findById(id)).thenReturn(getFarmer);
        when(farmerMapper.convertFarmerEntityToDto(getFarmer.get())).thenReturn(farmerById);

        //Act
        FarmerDTO result = farmerService.getFarmerById(id);

        //Assert
        assertNotNull(result);
        assertEquals(farmerById, result);
        assertEquals(farmerById.getFarmerName(), result.getFarmerName());
    }

    @Test
    void shouldThrowExceptionWhenFarmerNotExists() {
        //Arrange
        Long id = 1L;

        when(farmerRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, () -> farmerService.getFarmerById(id));

        //Assert
        assertEquals("Farmer was not found with id " + id, exception.getMessage());

        verify(farmerRepository, times(1)).findById(id);
        verify(farmerMapper, never()).convertFarmerEntityToDto(any(Farmer.class));
    }

    @Test
    void shouldDeleteWhenFarmerExists() {
        //Arrange
        Long id = 1L;

        when(farmerRepository.existsById(id)).thenReturn(true);

        //Act
        farmerService.deleteFarmer(id);

        //Assert
        assertFalse(farmerRepository.findById(id).isPresent());

        //Verify
        verify(farmerRepository, times(1)).existsById(id);
        verify(farmerRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenFarmerNotFoundToDelete() {
        //Arrange

        Long id = 1L;

        when(farmerRepository.existsById(id)).thenReturn(false);

        //Act & Assert
        FarmDataNotFoundException exception = assertThrows(FarmDataNotFoundException.class, ()->farmerService.deleteFarmer(id));

        assertEquals("No farmer with id " + id + " found.", exception.getMessage());

        verify(farmerRepository, times(1)).existsById(id);
        verify(farmerRepository, never()).deleteById(any());
    }
}