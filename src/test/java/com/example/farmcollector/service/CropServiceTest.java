package com.example.farmcollector.service;

import com.example.farmcollector.dto.CropDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CropServiceImplTest {

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
        farmer = new Farmer();
        farmer.setId(1L);

        farm = new Farm();
        farm.setId(1L);

        crop = new Crop();
        crop.setId(1L);
        crop.setCropType("Wheat");
        crop.setFarmer(farmer);
        crop.setFarm(farm);

        cropDTO = new CropDTO();
        cropDTO.setCropType("Wheat");
        cropDTO.setFarmer(farmer);
        cropDTO.setFarm(farm);
    }

    @Test
    void saveCrop_ShouldSaveAndReturnCropDTO() {
        when(cropMapper.convertCropDtoToEntity(cropDTO)).thenReturn(crop);
        when(cropRepository.save(crop)).thenReturn(crop);
        when(cropMapper.convertCropEntityToDto(crop)).thenReturn(cropDTO);

        CropDTO savedCropDTO = cropService.saveCrop(cropDTO);

        assertThat(savedCropDTO).isEqualTo(cropDTO);
        verify(cropRepository).save(crop);
        verify(cropMapper).convertCropDtoToEntity(cropDTO);
        verify(cropMapper).convertCropEntityToDto(crop);
    }

    @Test
    void getAllCrops_ShouldReturnListOfCropDTOs() {
        when(cropRepository.findAll()).thenReturn(List.of(crop));
        when(cropMapper.convertCropEntityToDto(crop)).thenReturn(cropDTO);

        List<CropDTO> cropDTOs = cropService.getAllCrops();

        assertThat(cropDTOs).containsExactly(cropDTO);
        verify(cropRepository).findAll();
        verify(cropMapper).convertCropEntityToDto(crop);
    }

    @Test
    void getCropById_ShouldReturnCropDTO_WhenCropExists() {
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));
        when(cropMapper.convertCropEntityToDto(crop)).thenReturn(cropDTO);

        CropDTO foundCropDTO = cropService.getCropById(1L);

        assertThat(foundCropDTO).isEqualTo(cropDTO);
        verify(cropRepository).findById(1L);
        verify(cropMapper).convertCropEntityToDto(crop);
    }

    @Test
    void getCropById_ShouldThrowException_WhenCropDoesNotExist() {
        when(cropRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cropService.getCropById(1L))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("Crop not found for the given ID");

        verify(cropRepository).findById(1L);
    }

    @Test
    void updateCropById_ShouldUpdateAndReturnCropDTO_WhenCropExists() {
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));
        when(cropMapper.convertCropDtoToEntity(cropDTO)).thenReturn(crop);
        when(cropRepository.save(crop)).thenReturn(crop);
        when(cropMapper.convertCropEntityToDto(crop)).thenReturn(cropDTO);

        CropDTO updatedCropDTO = cropService.updateCropById(1L, cropDTO);

        assertThat(updatedCropDTO).isEqualTo(cropDTO);
        verify(cropRepository).findById(1L);
        verify(cropMapper).convertCropDtoToEntity(cropDTO);
        verify(cropRepository).save(crop);
        verify(cropMapper).convertCropEntityToDto(crop);
    }

    @Test
    void updateCropById_ShouldThrowException_WhenCropDoesNotExist() {
        when(cropRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cropService.updateCropById(1L, cropDTO))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("No crop with ID 1 found.");

        verify(cropRepository).findById(1L);
    }

    @Test
    void deleteCrop_ShouldDeleteCrop_WhenCropExists() {
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));

        cropService.deleteCrop(1L);

        verify(cropRepository).findById(1L);
        verify(cropRepository).deleteById(1L);
    }

    @Test
    void deleteCrop_ShouldThrowException_WhenCropDoesNotExist() {
        when(cropRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cropService.deleteCrop(1L))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("Crop not found for the given ID");

        verify(cropRepository).findById(1L);
    }

    @Test
    void addFarmerToCrop_ShouldAddFarmerAndReturnUpdatedCropDTO() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.of(farmer));
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));
        when(cropRepository.save(crop)).thenReturn(crop);
        when(cropMapper.convertCropEntityToDto(crop)).thenReturn(cropDTO);

        CropDTO updatedCropDTO = cropService.addFarmerToCrop(1L, 1L);

        assertThat(updatedCropDTO).isEqualTo(cropDTO);
        verify(farmerRepository).findById(1L);
        verify(cropRepository).findById(1L);
        verify(cropRepository).save(crop);
        verify(cropMapper).convertCropEntityToDto(crop);
    }

    @Test
    void addFarmerToCrop_ShouldThrowException_WhenFarmerDoesNotExist() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cropService.addFarmerToCrop(1L, 1L))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("No farmer with the id");

        verify(farmerRepository).findById(1L);
    }

    @Test
    void addFarmerToCrop_ShouldThrowException_WhenCropDoesNotExist() {
        when(farmerRepository.findById(1L)).thenReturn(Optional.of(farmer));
        when(cropRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cropService.addFarmerToCrop(1L, 1L))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("No crop with this id");

        verify(farmerRepository).findById(1L);
        verify(cropRepository).findById(1L);
    }

    @Test
    void addFarmToCrop_ShouldAddFarmAndReturnUpdatedCropDTO() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(cropRepository.findById(1L)).thenReturn(Optional.of(crop));
        when(cropRepository.save(crop)).thenReturn(crop);
        when(cropMapper.convertCropEntityToDto(crop)).thenReturn(cropDTO);

        CropDTO updatedCropDTO = cropService.addFarmToCrop(1L, 1L);

        assertThat(updatedCropDTO).isEqualTo(cropDTO);
        verify(farmRepository).findById(1L);
        verify(cropRepository).findById(1L);
        verify(cropRepository).save(crop);
        verify(cropMapper).convertCropEntityToDto(crop);
    }

    @Test
    void addFarmToCrop_ShouldThrowException_WhenFarmDoesNotExist() {
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cropService.addFarmToCrop(1L, 1L))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("No fam with this id");

        verify(farmRepository).findById(1L);
    }

    @Test
    void addFarmToCrop_ShouldThrowException_WhenCropDoesNotExist() {
        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        when(cropRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cropService.addFarmToCrop(1L, 1L))
                .isInstanceOf(FarmDataNotFoundException.class)
                .hasMessage("No crop with this id");

        verify(farmRepository).findById(1L);
        verify(cropRepository).findById(1L);
    }
}
