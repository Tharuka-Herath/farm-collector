package com.example.farmcollector.service;

import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.enums.Season;
import com.example.farmcollector.repository.CropRepository;
import com.example.farmcollector.repository.FarmerRepository;
import com.example.farmcollector.service.crop.CropServiceImpl;
import com.example.farmcollector.util.CropMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CropServiceTest {
    @Mock
    private CropRepository cropRepository;

    @Mock
    CropMapper cropMapper;

    @Mock
    private FarmerRepository farmerRepository;

    @InjectMocks

    private CropServiceImpl cropService;




    @Test
    void saveCropTest() {
        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropType("Wheat");
        cropDTO.setSeason(Season.YALA);
        cropDTO.setYieldYear(2024);
        cropDTO.setExpectedAmount(150.0);
        cropDTO.setActualAmount(120.0);

        Crop crop = new Crop();
        crop.setId(1L);
        crop.setCropId("C-0001");
        crop.setCropType("Wheat");
        crop.setSeason(Season.YALA);
        crop.setYieldYear(2024);
        crop.setExpectedAmount(150.0);
        crop.setActualAmount(120.0);

        Crop savedCrop = new Crop();
        savedCrop.setCropId("C-0001");
        savedCrop.setCropType("Wheat");
        savedCrop.setSeason(Season.YALA);
        savedCrop.setYieldYear(2024);
        savedCrop.setExpectedAmount(150.0);
        savedCrop.setActualAmount(120.0);

        CropDTO savedCropDto = new CropDTO();
        savedCropDto.setCropId("C-0001");
        savedCropDto.setCropType("Wheat");
        savedCropDto.setSeason(Season.YALA);
        savedCropDto.setYieldYear(2024);
        savedCropDto.setExpectedAmount(150.0);
        savedCropDto.setActualAmount(120.0);

        when(cropMapper.convertCropDtoToEntity(cropDTO)).thenReturn(crop);
        when(cropRepository.save(crop)).thenReturn(savedCrop);
        when(cropMapper.convertCropEntityToDto(savedCrop)).thenReturn(savedCropDto);

        CropDTO actualSave = cropService.saveCrop(cropDTO);

        assertEquals(savedCropDto, actualSave);
        assertEquals(savedCropDto.getCropId(),actualSave.getCropId());
        assertEquals(savedCropDto.getSeason(),actualSave.getSeason());
    }

    @Test
    void updateCropIfAvailableTest(){
        Long id=1L;

        CropDTO cropDTO = new CropDTO("C-0001","Carrot",Season.YALA,
                2024,200.0,150.0,null,null);
        Optional<Crop> updatedCrop = Optional.of(new Crop(id,"C-0001","Wheat",Season.YALA,
                2024,150.0,120.0,null,null,null,null));

        Crop updatedCropEntity = new Crop(id,"C-0001","Carrot",Season.YALA,
                2024,200.0,150.0,null,null,null,null);

        CropDTO updatedCropDTO = new CropDTO("C-0001","Carrot",Season.YALA,
                2024,200.0,150.0,null,null);

        when(cropRepository.findById(id)).thenReturn(updatedCrop);
        when(cropMapper.convertCropDtoToEntity(cropDTO)).thenReturn(updatedCropEntity);
        when(cropMapper.convertCropEntityToDto(updatedCropEntity)).thenReturn(updatedCropDTO);
        when(cropRepository.save(updatedCropEntity)).thenReturn(updatedCropEntity);

        CropDTO actualUpdate = cropService.updateCropById(id,cropDTO);

        assertEquals(updatedCropDTO,actualUpdate);


        verify(cropRepository, times(1)).findById(id);
        verify(cropMapper, times(1)).convertCropDtoToEntity(cropDTO);
        verify(cropRepository, times(1)).save(updatedCropEntity);
        verify(cropMapper, times(1)).convertCropEntityToDto(updatedCropEntity);


    }

    @Test
    void getCropByIdTest(){
        Long id = 1L;
        Optional<Crop> cropById= Optional.of(new Crop(id,"C-0001","Wheat",Season.YALA,
                2024,150.0,120.0,null,null,null,null));




        CropDTO convertedcropDTO = new CropDTO("C-0001","Wheat",Season.YALA,
                2024,150.0,120.0,null,null);



        when(cropRepository.findById(id)).thenReturn(cropById);
        when(cropMapper.convertCropEntityToDto(cropById.get())).thenReturn(convertedcropDTO);

        CropDTO results = cropService.getCropById(id);

        assertEquals(convertedcropDTO,results);
        assertNotNull(results);

        verify(cropRepository, times(1)).findById(id);
        verify(cropMapper, times(1)).convertCropEntityToDto(cropById.get());




    }

    @Test
    void isThatFarmerAddedToCrop(){
        Long cropId=1L;
        Long farmerId=1L;

        Farmer farmer = new Farmer(farmerId,"F-0001","nimal",null,null,null,null);
        Crop crop = new Crop(cropId,"C-0001","Wheat",Season.YALA,
                2024,150.0,120.0,null,null,null,null);

       Crop addedCrop = new Crop(cropId,"C-0001","Wheat",Season.YALA,
                2024,150.0,120.0,null,null,null,farmer);

       CropDTO cropDTO = new CropDTO("C-0001","Wheat",Season.YALA,
                2024,150.0,120.0,null,farmer);

         when(cropRepository.findById(cropId)).thenReturn(Optional.of(crop));
         when(farmerRepository.findById(farmerId)).thenReturn(Optional.of(farmer));
         when(cropRepository.save(any(Crop.class))).thenReturn(addedCrop);
         when(cropMapper.convertCropEntityToDto(addedCrop)).thenReturn(cropDTO);

         CropDTO actualCrop = cropService.addFarmerToCrop(cropId,farmerId);

         assertEquals(cropDTO,actualCrop);
        assertEquals(farmer, addedCrop.getFarmer());

        verify(farmerRepository, times(1)).findById(farmerId);
        verify(cropRepository, times(1)).findById(cropId);
        verify(cropRepository, times(1)).save(any(Crop.class));
        verify(cropMapper, times(1)).convertCropEntityToDto(addedCrop);


    }

    @Test
    void getAllCropsTest() {
        List<Crop> crops = Arrays.asList(
                new Crop(1L, "C-0001", "Wheat", Season.YALA, 2024, 150.0, 120.0, null, null, null, null),
                new Crop(2L, "C-0002", "Corn", Season.MAHA, 2024, 200.0, 180.0, null, null, null, null)
        );

        List<CropDTO> cropDTOs = Arrays.asList(
                new CropDTO("C-0001", "Wheat", Season.YALA, 2024, 150.0, 120.0, null, null),
                new CropDTO("C-0002", "Corn", Season.MAHA, 2024, 200.0, 180.0, null, null)
        );

        when(cropRepository.findAll()).thenReturn(crops);
        when(cropMapper.convertCropEntityToDto(crops.get(0))).thenReturn(cropDTOs.get(0));
        when(cropMapper.convertCropEntityToDto(crops.get(1))).thenReturn(cropDTOs.get(1));

        List<CropDTO> result = cropService.getAllCrops();

        assertEquals(2, result.size());
        assertEquals(cropDTOs.get(0), result.get(0));
        assertEquals(cropDTOs.get(1), result.get(1));

        verify(cropRepository, times(1)).findAll();
        verify(cropMapper, times(1)).convertCropEntityToDto(crops.get(0));
        verify(cropMapper, times(1)).convertCropEntityToDto(crops.get(1));

        assertNotNull(result);
        assertFalse(result.isEmpty());

    }


    @Test
    void testDeleteExistingCrop() {

        Long id = 1L;
        Crop crop = new Crop();
        crop.setId(id);
        when(cropRepository.findById(id)).thenReturn(Optional.of(crop));


        cropService.deleteCrop(id);


        verify(cropRepository, times(1)).deleteById(id);
    }





}