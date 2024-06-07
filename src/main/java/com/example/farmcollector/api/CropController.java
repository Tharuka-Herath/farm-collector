package com.example.farmcollector.api;

import com.example.farmcollector.api.request.CropRequest;
import com.example.farmcollector.api.response.CropResponse;
import com.example.farmcollector.dto.CropDTO;
import com.example.farmcollector.exception.FarmDataNotFoundException;
import com.example.farmcollector.service.crop.CropService;
import com.example.farmcollector.util.CropMapper;
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

    @GetMapping("/{id}")
    public ResponseEntity<CropResponse> getCropById(@PathVariable Long id) {
        try {
            CropDTO cropDTO = cropService.getCropById(id);
            CropResponse response = cropMapper.convertDtoToResponse(cropDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (FarmDataNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CropResponse> updateCropById(@PathVariable Long id, @RequestBody CropRequest request) {
        try {
            CropDTO cropDTO = cropMapper.convertCropRequestToDto(request);
            CropResponse cropResponse = cropMapper.convertDtoToResponse(cropService.updateCropById(id, cropDTO));
            return new ResponseEntity<>(cropResponse, HttpStatus.OK);
        } catch (FarmDataNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCropById(@PathVariable Long id) {
        try {
            cropService.deleteCrop(id);
            return ResponseEntity.noContent().build();
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
}