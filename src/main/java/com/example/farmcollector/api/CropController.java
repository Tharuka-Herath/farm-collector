package com.example.farmcollector.api;

import com.example.farmcollector.api.request.CropRequest;
import com.example.farmcollector.api.response.CropResponse;
import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.enums.Season;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.model.Crop;
import com.example.farmcollector.service.crop.CropService;
import com.example.farmcollector.util.CropMapper;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crops")
public class CropController {
    private final CropService cropService;
    private final CropMapper cropMapper;

    public CropController(CropService cropService, CropMapper cropMapper) {
        this.cropService = cropService;
        this.cropMapper = cropMapper;
    }

    @PostMapping
    public ResponseEntity<CropResponse> createCrop(@RequestBody CropRequest cropRequest) {
        CropDTO cropDTO = cropMapper.convertCropRequestToDto(cropRequest);
        CropResponse response = cropMapper.convertDtoToResponse(cropService.saveCrop(cropDTO));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CropResponse>> getAllCrops() {
        List<CropDTO> crops = cropService.getAllCrops();
        List<CropResponse> responseList = cropMapper.convertDtoListToResponseList(crops);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/crop-by-Id")
    public ResponseEntity<CropResponse> getCropById(@RequestParam("cropId") String cropId) {
        try {
            CropDTO cropDTO = cropService.getCropById(cropId);
            CropResponse response = cropMapper.convertDtoToResponse(cropDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (FarmDataNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{cropId}")
    public ResponseEntity<CropResponse> updateCropById(@PathVariable String cropId, @RequestBody CropRequest request) {
        try {
            CropDTO cropDTO = cropMapper.convertCropRequestToDto(request);
            CropResponse cropResponse = cropMapper.convertDtoToResponse(cropService.updateCropById(cropId, cropDTO));
            return new ResponseEntity<>(cropResponse, HttpStatus.OK);
        } catch (FarmDataNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteCropById(@RequestParam("cropId") String cropId) {
        try {
            cropService.deleteCropByCropId(cropId);
            return ResponseEntity.ok().build();
        } catch (FarmDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{cropId}/addFarmer/{farmerId}")
    public ResponseEntity<CropResponse> addFarmerToCrop(@PathVariable Long cropId, @PathVariable Long farmerId) {
        CropResponse cropResponse = cropMapper.convertDtoToResponse(cropService.addFarmerToCrop(cropId, farmerId));

        return ResponseEntity.status(HttpStatus.OK).body(cropResponse);
    }

    @PostMapping("/{cropId}/addFarm/{farmId}")
    public ResponseEntity<CropResponse> addFarmToCrop(@PathVariable Long cropId, @PathVariable Long farmId) {
        CropResponse cropResponse = cropMapper.convertDtoToResponse(cropService.addFarmToCrop(cropId, farmId));
        return ResponseEntity.status(HttpStatus.OK).body(cropResponse);
    }

    @GetMapping("/by-crop-type")
    public ResponseEntity<List<CropResponse>> getAllCropsByCropType(@RequestParam String cropType) {
        List<CropDTO> crops = cropService.findAllByCropType(cropType);
        return ResponseEntity.status(HttpStatus.OK).body(cropMapper.convertDtoListToResponseList(crops));
    }

    @GetMapping("/average-yield")
    public ResponseEntity<Double> getAverageYieldBySeasonAndYear(@RequestParam Season season, @RequestParam Integer year) {
        Double averageYield = cropService.findAverageYieldBySeasonAndYear(season, year);
        return new ResponseEntity<>(averageYield, HttpStatus.OK);
    }

    @GetMapping("/with-farm-location")
    public ResponseEntity<List<Object[]>> getCropsWithFarmLocationByCropType(@RequestParam String cropType) {
        List<Object[]> cropsWithFarmLocation = cropService.findCropsWithFarmLocationByCropType(cropType);
        return new ResponseEntity<>(cropsWithFarmLocation, HttpStatus.OK);
    }

    @GetMapping("/with-farm-season")
    public ResponseEntity<List<Object[]>> getCropsByFarmNameAndSeason(@RequestParam String farmName, @RequestParam Season season) {
        List<Object[]> cropsWithFarmNameAndSeason = cropService.findCropsByFarmNameAndSeason(farmName, season);
        return new ResponseEntity<>(cropsWithFarmNameAndSeason,HttpStatus.OK);
    }
}